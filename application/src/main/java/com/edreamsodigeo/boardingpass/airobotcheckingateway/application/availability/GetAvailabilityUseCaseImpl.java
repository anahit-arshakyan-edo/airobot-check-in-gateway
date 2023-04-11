package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GetAvailabilityUseCaseImpl implements GetAvailabilityUseCase {

    private final GetAvailabilityOutboundPort airobot;

    @Inject
    public GetAvailabilityUseCaseImpl(GetAvailabilityOutboundPort airobot) {
        this.airobot = airobot;
    }

    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        return airobot.getAvailability(availabilityRequest);
    }
}
