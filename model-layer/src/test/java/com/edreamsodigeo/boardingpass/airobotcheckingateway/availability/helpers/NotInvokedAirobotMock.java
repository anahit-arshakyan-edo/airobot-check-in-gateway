package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Airobot;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.AvailabilityRequest;

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
