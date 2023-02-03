package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityRequest {

    private final Section section;
    private final Passenger passenger;

    public AvailabilityRequest(Section section) {
        this(section, new Passenger());
    }

    public AvailabilityRequest(Section section, Passenger passenger) {
        this.section = section;
        this.passenger = passenger != null ? passenger : new Passenger();
    }

    public boolean isValid() {
        return section != null && section.isValid();
    }
}
