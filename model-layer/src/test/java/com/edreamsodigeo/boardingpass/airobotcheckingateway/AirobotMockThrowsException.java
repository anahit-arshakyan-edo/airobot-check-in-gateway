package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AirobotMockThrowsException implements Airobot {

    public AirobotMockThrowsException() {
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        throw new RuntimeException();
    }
}
