package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.AvailabilityRequest;

public interface GetAvailabilityOutboundPort {
    Availability getAvailability(AvailabilityRequest availabilityRequest);
}
