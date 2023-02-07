package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Airobot;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.AvailabilityRequest;

public class AirobotMockThrowsException implements Airobot {

    public AirobotMockThrowsException() {
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        throw new RuntimeException();
    }
}
