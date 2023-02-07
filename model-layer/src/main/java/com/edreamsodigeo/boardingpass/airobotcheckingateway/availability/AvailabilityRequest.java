package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import java.util.List;

public class AvailabilityRequest {

    private final List<Section> sections;
    private final Passenger passenger;

    public AvailabilityRequest(List<Section> sections, Passenger passenger) {
        this.sections = sections;
        this.passenger = passenger != null ? passenger : new Passenger();
    }

    public boolean isValid() {
        return sections != null
                && !sections.isEmpty()
                && sections.get(0).isValid()
                && !containsNull(sections);
    }

    private static boolean containsNull(List<Section> sections) {
        return sections.stream().anyMatch(item -> item == null);
    }

}
