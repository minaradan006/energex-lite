package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import documents.BaseDocument;
import fileio.CommandInput;
import fileio.CommandParametersInput;
import repository.MarketDataRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The GetCommand class handles retrieval operations for market data,
 * supporting range queries, target resolution (including "LATEST"), and subscription tier checks.
 */
public final class GetCommand implements Command {
    private static final int INTERVAL_DURATION_MINUTES = 15;
    private final CommandInput cmdInput;

    /**
     * Constructs a GetCommand with the specified command input parameters.
     */
    public GetCommand(final CommandInput cmdInput) {
        this.cmdInput = cmdInput;
    }

    @Override
    public void execute(final MarketDataRepository repository,
                        final ObjectMapper mapper,
                        final ArrayNode output,
                        final boolean isImportActive) {
        CommandParametersInput params = cmdInput.getParameters();
        if (params == null) {
            return;
        }

        String clientTier = params.getClientTier();
        String dataType = params.getDataType();
        String timestamp = cmdInput.getTimestamp();

        String from;
        String to;

        if (params.getTarget() != null) {
            if ("LATEST".equalsIgnoreCase(params.getTarget())) {
                String latestStart = repository.findLatestStart(dataType, timestamp);
                if (latestStart != null) {
                    from = latestStart;
                    to = calculateEndTime(latestStart);
                } else {
                    from = params.getTarget();
                    to = calculateEndTime(params.getTarget());
                }
            } else {
                from = params.getTarget();
                to = calculateEndTime(params.getTarget());
            }
        } else {
            from = params.getStart();
            to = params.getEnd();
        }

        ObjectNode responseNode = mapper.createObjectNode();
        responseNode.put("command", "get");
        responseNode.put("timestamp", timestamp);

        if ("FREE".equalsIgnoreCase(clientTier)
                && dataType != null
                && "DAM_PRICE_PREDICTION".equalsIgnoreCase(dataType)) {
            responseNode.put("status", "ERROR");
            responseNode.put("from", from);
            responseNode.put("to", to);
            responseNode.put("message",
                    "Access denied: " + dataType + " requires PREMIUM subscription.");
            output.add(responseNode);
            return;
        }

        List<BaseDocument> docs = repository.findByCriteriaRange(dataType, from, to, timestamp);

        if (docs != null && !docs.isEmpty()) {
            responseNode.put("status", "SUCCESS");
            responseNode.put("from", from);
            responseNode.put("to", to);
            responseNode.set("data", mapper.valueToTree(docs));
        } else {
            responseNode.put("status", "WARNING");
            responseNode.put("from", from);
            responseNode.put("to", to);
            responseNode.putNull("data");
            responseNode.put("message", "Data is not yet available. Try again later.");
        }

        output.add(responseNode);
    }

    private String calculateEndTime(final String targetTimestamp) {
        try {
            LocalDateTime dt = LocalDateTime.parse(targetTimestamp);
            return dt.plusMinutes(INTERVAL_DURATION_MINUTES).toString();
        } catch (Exception e) {
            return targetTimestamp;
        }
    }
}
