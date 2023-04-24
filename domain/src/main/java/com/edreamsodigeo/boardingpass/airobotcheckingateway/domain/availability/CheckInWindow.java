package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import java.time.Duration;
import java.util.Objects;

public class CheckInWindow {
    private final Duration openingTime;
    private final Duration closingTime;

    public CheckInWindow(Duration openingTime, Duration closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Duration openingTime() {
        return openingTime;
    }

    public Duration closingTime() {
        return closingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckInWindow that = (CheckInWindow) o;
        return openingTime.equals(that.openingTime) && closingTime.equals(that.closingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openingTime, closingTime);
    }

    @Override
    public String toString() {
        return "CheckInWindow{"
                + "openingTime=" + openingTime
                + ", closingTime=" + closingTime
                + '}';
    }
}
