package clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulationClock {
    private static SimulationClock instance;
    private String currentTime;
    private boolean active;

    private SimulationClock() {
        active = false;
    }

    public static synchronized SimulationClock getInstance() {
        if (instance == null) {
            instance = new SimulationClock();
        }
        return instance;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
