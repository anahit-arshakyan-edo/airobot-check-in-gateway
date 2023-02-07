package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class AvailabilityResult {
    private boolean validRequest = false;

    public static final AvailabilityResult INVALID_REQUEST_ERROR = new AvailabilityResult(null, false);

    private final Availability availability;

    public static AvailabilityResult forValidRequest(Availability availability) {
        return new AvailabilityResult(availability, true);
    }

    private AvailabilityResult(Availability availability, boolean validRequest) {
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
