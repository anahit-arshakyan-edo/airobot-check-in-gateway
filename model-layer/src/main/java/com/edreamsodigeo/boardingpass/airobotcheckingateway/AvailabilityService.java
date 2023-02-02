package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityService {
    private final Airobot airobot;

    public AvailabilityService(Airobot airobot) {
        this.airobot = airobot;
    }

    public AvailabilityResult getAvailability(AvailabilityRequest availabilityRequest) {
        if (isNotValid(availabilityRequest)) {
            return AvailabilityResult.ERROR_INVALID_REQUEST;
        }
        Availability availability = airobot.getAvailability(availabilityRequest);
        return new AvailabilityResult(availability);
    }

    private boolean isNotValid(AvailabilityRequest availabilityRequest) {
        return availabilityRequest.getDepartureAirport() == null || "".equalsIgnoreCase(availabilityRequest.getDepartureAirport());
    }
}
