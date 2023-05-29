package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;

public interface GenerateReferenceIdOutboundPort {
    ProviderReferenceId generateReferenceId();
}
