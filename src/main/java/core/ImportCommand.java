package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import documents.BaseGridLoad;
import documents.Interval;
import fileio.CommandInput;
import fileio.CommandParametersInput;
import fileio.MarketDataInput;
import repository.MarketDataRepository;

/**
 * The ImportCommand class handles the execution of import and patch commands,
 * validating data points and storing them in the repository or issuing warnings.
 */
public final class ImportCommand implements Command {
    private final CommandInput cmdInput;
    private final String commandType;

    /**
     * Constructs an ImportCommand with the given command input and type.
     */
    public ImportCommand(final CommandInput cmdInput, final String commandType) {
        this.cmdInput = cmdInput;
        this.commandType = commandType;
    }

    @Override
    public void execute(final MarketDataRepository repository,
                        final ObjectMapper mapper,
                        final ArrayNode output,
                        final boolean isImportActive) {
        if ("import".equalsIgnoreCase(commandType) && !isImportActive) {
            return;
        }

        CommandParametersInput params = cmdInput.getParameters();
        if (params == null || params.getData() == null) {
            return;
        }

        String generalDataType = params.getDataType();
        String timestamp = cmdInput.getTimestamp();

        for (MarketDataInput pointInput : params.getData()) {
            String dataType = pointInput.id() != null ? pointInput.id() : generalDataType;

            String formattedInterval = pointInput.interval();
            if (formattedInterval != null
                    && formattedInterval.contains("-")
                    && !formattedInterval.contains(" - ")) {
                formattedInterval = formattedInterval.replace("-", " - ");
            }

            Interval interval = IntervalParser.parseIntervalToSpecificType(dataType, pointInput);
            if (interval != null) {
                int intervalIndex = IntervalParser.extractIntervalIndex(pointInput.interval());
                if (!interval.isValid()) {
                    if ("import".equalsIgnoreCase(commandType)) {
                        repository.removePoint(dataType, pointInput.date(), intervalIndex);
                    }
                    emitWarning(output, mapper, commandType, timestamp, dataType,
                            pointInput.date(), formattedInterval, intervalIndex,
                            interval.getValidationError());
                } else {
                    String validationError = null;
                    if ("GRID_LOAD".equalsIgnoreCase(dataType)
                            && interval instanceof BaseGridLoad.GridLoadInterval) {
                        validationError = repository.validateAndUpsertGridLoad(
                                dataType, pointInput.date(),
                                (BaseGridLoad.GridLoadInterval) interval);
                    } else {
                        repository.upsertPoint(dataType, pointInput.date(), interval);
                    }

                    if (validationError != null) {
                        emitWarning(output, mapper, commandType, timestamp, dataType,
                                pointInput.date(), formattedInterval, intervalIndex,
                                validationError);
                    }
                }
            }
        }
    }

    private void emitWarning(final ArrayNode output,
                             final ObjectMapper mapper,
                             final String command,
                             final String timestamp,
                             final String dataType,
                             final String date,
                             final String interval,
                             final int intervalIndex,
                             final String message) {
        ObjectNode warningNode = mapper.createObjectNode();
        warningNode.put("command", command);
        warningNode.put("timestamp", timestamp);
        warningNode.put("status", "WARNING");
        warningNode.put("id", dataType);
        warningNode.put("date", date);
        warningNode.put("interval", interval);
        warningNode.put("intervalIndex", intervalIndex);
        warningNode.put("message", message);
        output.add(warningNode);
    }
}
