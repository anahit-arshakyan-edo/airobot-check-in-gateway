package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityService {
    private final Airobot airobot;

    public AvailabilityService(Airobot airobot) {
        this.airobot = airobot;
    }

    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        return airobot.getAvailability(availabilityRequest);
    }
}
