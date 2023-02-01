package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AirobotMock implements Airobot {
    private Availability returnedAvailability;

    public AirobotMock(Availability returnedAvailability) {
        this.returnedAvailability = returnedAvailability;
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        return returnedAvailability;
    }
}
