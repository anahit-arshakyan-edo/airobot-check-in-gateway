package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;

public interface AirobotOutboundPort {
    Availability getAvailability(AvailabilityRequest availabilityRequest);
}
