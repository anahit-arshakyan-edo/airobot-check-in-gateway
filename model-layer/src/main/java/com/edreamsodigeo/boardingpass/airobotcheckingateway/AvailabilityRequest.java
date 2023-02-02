package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityRequest {

    private Section section;
    private Passenger passenger;

    public AvailabilityRequest(Section section) {
        this(section, new Passenger());
    }

    public AvailabilityRequest(Section section, Passenger passenger) {
        this.section = section;
        this.passenger = passenger;
    }

    public boolean isValid() {
        return section.isValid();
    }
}
