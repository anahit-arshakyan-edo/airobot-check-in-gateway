package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import java.util.List;

public class AvailabilityRequest {

    private final List<Section> sections;
    private final Passengers passengers;

    public AvailabilityRequest(List<Section> sections, Passengers passengers) {
        this.sections = sections;
        this.passengers = passengers != null ? passengers : new Passengers();
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
