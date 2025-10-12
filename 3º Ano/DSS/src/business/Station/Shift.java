package business.Station;

import java.time.LocalTime;
import java.util.Objects;

public class Shift {

    private final LocalTime startTime;
    private final LocalTime endTime;

    public Shift(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shift shift)) return false;
        return Objects.equals(getStartTime(), shift.getStartTime()) && Objects.equals(getEndTime(), shift.getEndTime());
    }

    @Override
    public String toString() {
        return "Shift{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
