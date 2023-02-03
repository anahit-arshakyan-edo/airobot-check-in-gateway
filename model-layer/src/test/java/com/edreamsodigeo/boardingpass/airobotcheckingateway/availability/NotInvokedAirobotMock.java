package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class NotInvokedAirobotMock implements Airobot {

    private boolean invoked;

    public NotInvokedAirobotMock() {
        this.invoked = false;
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        this.invoked = true;
        return null;
    }

    public boolean isInvoked() {
        return invoked;
    }
}
