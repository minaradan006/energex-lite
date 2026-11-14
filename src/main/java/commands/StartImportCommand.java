package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import repository.MarketDataRepository;
import clock.SimulationClock;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartImportCommand extends Command {
    @Override
    public void execute(MarketDataRepository repository) {
        String targetTimestamp = getTimestamp();

        SimulationClock.getInstance().setCurrentTime(targetTimestamp);
        SimulationClock.getInstance().setActive(true);
    }
}
