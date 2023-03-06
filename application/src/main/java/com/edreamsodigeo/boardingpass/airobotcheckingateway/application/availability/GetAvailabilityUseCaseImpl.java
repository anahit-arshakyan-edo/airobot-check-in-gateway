package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GetAvailabilityUseCaseImpl implements GetAvailabilityUseCase {

    private final AirobotOutboundPort airobot;

    @Inject
    public GetAvailabilityUseCaseImpl(AirobotOutboundPort airobot) {
        this.airobot = airobot;
    }

    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        return airobot.getAvailability(availabilityRequest);
    }
}
