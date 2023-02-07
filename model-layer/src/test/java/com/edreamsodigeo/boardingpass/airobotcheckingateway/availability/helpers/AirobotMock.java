package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Airobot;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.AvailabilityRequest;

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
