package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.Collections;
import java.util.List;

public class AvailabilityRequest {

    private final List<Section> sections;
    private final Passengers passengers;

    public AvailabilityRequest(List<Section> sections, Passengers passengers) {
        this.sections = sections;
        this.passengers = passengers != null ? passengers : Passengers.EMPTY_PASSENGERS;
    }

    public boolean isValid() {
        return passengers != null
                && sections != null
                && !sections.isEmpty()
                && allValid(sections);
    }

    private static boolean allValid(List<Section> sections) {
        return sections.stream().allMatch(section -> section != null && section.isValid());
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Passengers getPassengers() {
        return passengers;
    }
}
