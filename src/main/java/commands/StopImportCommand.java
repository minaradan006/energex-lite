package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import repository.MarketDataRepository;
import clock.SimulationClock;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StopImportCommand extends Command {
    @Override
    public void execute(MarketDataRepository repository) {
        String currentTimestamp = getTimestamp();
        if (currentTimestamp != null) {
            SimulationClock.getInstance().setCurrentTime(currentTimestamp);
        }

        SimulationClock.getInstance().setActive(false);
    }
}
