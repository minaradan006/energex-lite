package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import repository.MarketDataRepository;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartImportCommand extends Command {
    @Override
    public void execute(MarketDataRepository repository) {

    }
}
