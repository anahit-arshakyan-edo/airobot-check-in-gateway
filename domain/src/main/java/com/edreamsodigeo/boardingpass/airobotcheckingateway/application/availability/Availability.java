package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Availability {

    private boolean available;
    private boolean requiresApi;
    private List<CheckInAvailability> availabilities;

    public Availability() {
        this(false);
    }

    public Availability(boolean available) {
        this(available, false);
    }

    public Availability(boolean available, boolean requiresApi) {
        this(available, requiresApi, new ArrayList<>());
    }

    public Availability(boolean available, boolean requiresApi, List<CheckInAvailability> availabilities) {
        this.available = available;
        this.requiresApi = requiresApi;
        this.availabilities = availabilities == null ? Collections.emptyList() : availabilities;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isRequiresApi() {
        return requiresApi;
    }

    public List<CheckInAvailability> availabilities() {
        return Collections.unmodifiableList(availabilities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Availability that = (Availability) o;
        return available == that.available && requiresApi == that.requiresApi && availabilities.equals(that.availabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(available, requiresApi, availabilities);
    }

    @Override
    public String toString() {
        return "Availability{"
                + "available=" + available
                + ", requiresApi=" + requiresApi
                + ", availabilities=" + availabilities
                + '}';
    }
}

