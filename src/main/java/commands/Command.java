package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import repository.MarketDataRepository;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "command",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartImportCommand.class, name = "startImport"),
        @JsonSubTypes.Type(value = StopImportCommand.class, name = "stopImport"),
        @JsonSubTypes.Type(value = ImportCommand.class, name = "import"),
        @JsonSubTypes.Type(value = PatchCommand.class, name = "patch"),
        @JsonSubTypes.Type(value = GetCommand.class, name = "get")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Command {
    private String command;
    private String timestamp;

    public abstract void execute(MarketDataRepository repository);

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
