package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityResult {
    public static final AvailabilityResult ERROR_INVALID_REQUEST = new AvailabilityResult(null);

    private final Availability availability;

    public AvailabilityResult(Availability availability) {
        this.availability = availability;
    }

    public boolean isInvalidRequest() {
        return true;
    }

    public Availability getAvailability() {
        return availability;
    }
}
