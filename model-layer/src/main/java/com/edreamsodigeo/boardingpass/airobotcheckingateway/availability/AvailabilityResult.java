package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class AvailabilityResult {
    private boolean validRequest = false;

    public static final AvailabilityResult ERROR_INVALID_REQUEST = new AvailabilityResult(null, false);

    private final Availability availability;

    public AvailabilityResult(Availability availability) {
        this.availability = availability;
        this.validRequest = true;
    }

    public AvailabilityResult(Availability availability, boolean validRequest) {
        this.availability = availability;
        this.validRequest = validRequest;
    }

    public boolean isValidRequest() {
        return validRequest;
    }

    public Availability getAvailability() {
        return availability;
    }
}
