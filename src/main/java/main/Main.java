package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import core.CommandProcessor;
import fileio.InputLoader;

import java.io.File;
import java.io.IOException;

/**
 * The Main class is the entry point for the market data processing. It coordinates
 * commands from input files, serializing the resulting output to the specified file path.
 */
public final class Main {

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * Processes input commands from a file and writes the resulting JSON response to the
     * output file.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        CommandProcessor processor = new CommandProcessor();
        processor.processCommands(inputPath, output);

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
