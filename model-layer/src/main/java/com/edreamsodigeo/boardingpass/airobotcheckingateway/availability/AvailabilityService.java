package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class AvailabilityService {
    private final Airobot airobot;

    public AvailabilityService(Airobot airobot) {
        this.airobot = airobot;
    }

    public AvailabilityResult getAvailability(AvailabilityRequest availabilityRequest) {
        if (!availabilityRequest.isValid()) {
            return AvailabilityResult.ERROR_INVALID_REQUEST;
        }

        Availability availability = airobot.getAvailability(availabilityRequest);
        return new AvailabilityResult(availability);
    }

}
