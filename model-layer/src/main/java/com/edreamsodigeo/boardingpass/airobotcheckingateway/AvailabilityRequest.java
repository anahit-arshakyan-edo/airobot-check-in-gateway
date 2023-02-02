package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityRequest {

    private Section section;

    public AvailabilityRequest(Section section) {
        this.section = section;
    }

    public boolean isValid() {
        return section.isValid();
    }
}
