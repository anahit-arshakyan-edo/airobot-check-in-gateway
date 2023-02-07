package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import java.util.List;

public class AvailabilityRequest {

    private final List<Section> section;
    private final Passenger passenger;

    public AvailabilityRequest(List<Section> sections) {
        this(sections, new Passenger());
    }

    public AvailabilityRequest(List<Section> sections, Passenger passenger) {
        this.section = sections;
        this.passenger = passenger != null ? passenger : new Passenger();
    }

    public boolean isValid() {
        return section != null
                && !section.isEmpty()
                && section.get(0).isValid()
                && !containsNull();
    }

    private boolean containsNull() {
        return section.stream().anyMatch(item -> item == null);
    }

}
