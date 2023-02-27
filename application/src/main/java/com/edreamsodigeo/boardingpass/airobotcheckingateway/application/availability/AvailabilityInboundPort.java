package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AvailabilityInboundPort implements AvailabilityUseCase {

    private final AirobotOutboundPort airobot;

    @Inject
    public AvailabilityInboundPort(AirobotOutboundPort airobot) {
        this.airobot = airobot;
    }

    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        if (!availabilityRequest.isValid()) {
            throw new InvalidAvailabilityRequestException();
        }

        return airobot.getAvailability(availabilityRequest);
    }
}
