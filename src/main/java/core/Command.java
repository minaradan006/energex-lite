package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import repository.MarketDataRepository;

/**
 * The Command interface represents an executable operation within the market data system,
 * following the Command Pattern. Concrete command classes implement specific handling logic.
 */
public interface Command {
    /**
     * Executes the command using the provided repository, object mapper, output stream, and
     * import state.
     */
    void execute(MarketDataRepository repository, ObjectMapper mapper,
                 ArrayNode output, boolean isImportActive);
}
