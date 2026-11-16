package core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import fileio.InputLoader;
import repository.MarketDataRepository;

import java.io.IOException;
import java.util.List;

/**
 * The CommandProcessor class is responsible for reading input commands,
 * managing the import state, and managing the execution of operations
 * such as imports, patches, and data retrievals.
 */
public final class CommandProcessor {
    private final MarketDataRepository repository = new MarketDataRepository();
    private final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private boolean isImportActive = false;

    /**
     * Processes the list of commands loaded from the specified input file path
     * and records the execution results into the output JSON array.
     */
    public void processCommands(final String inputPath, final ArrayNode output) throws IOException {
        InputLoader loader = new InputLoader(inputPath);
        List<CommandInput> commands = loader.getCommands();

        for (CommandInput cmdInput : commands) {
            String commandName = cmdInput.getCommand();
            if (commandName == null) {
                continue;
            }

            Command command = null;
            switch (commandName.toLowerCase()) {
                case "startimport":
                    isImportActive = true;
                    break;
                case "stopimport":
                    isImportActive = false;
                    break;
                case "import":
                    command = new ImportCommand(cmdInput, "import");
                    break;
                case "patch":
                    command = new ImportCommand(cmdInput, "patch");
                    break;
                case "get":
                    command = new GetCommand(cmdInput);
                    break;
                default:
                    break;
            }

            if (command != null) {
                command.execute(repository, mapper, output, isImportActive);
            }
        }
    }
}
