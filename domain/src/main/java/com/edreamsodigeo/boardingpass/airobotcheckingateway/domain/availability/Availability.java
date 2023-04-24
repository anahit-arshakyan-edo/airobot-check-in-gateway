package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Availability {

    private final boolean available;
    private final boolean requiresApi;
    private final List<SectionAvailability> sectionAvailabilities;

    public Availability(boolean available, boolean requiresApi, List<SectionAvailability> sectionAvailabilities) {
        this.available = available;
        this.requiresApi = requiresApi;
        this.sectionAvailabilities = sectionAvailabilities == null ? Collections.emptyList() : sectionAvailabilities;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isRequiresApi() {
        return requiresApi;
    }

    public List<SectionAvailability> sectionAvailabilities() {
        return Collections.unmodifiableList(sectionAvailabilities);
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
        return available == that.available && requiresApi == that.requiresApi && sectionAvailabilities.equals(that.sectionAvailabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(available, requiresApi, sectionAvailabilities);
    }

    @Override
    public String toString() {
        return "Availability{"
                + "available=" + available
                + ", requiresApi=" + requiresApi
                + ", availabilities=" + sectionAvailabilities
                + '}';
    }
}

