package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;

public interface RequestCheckInOutboundPort {
    void requestCheckIn(ProviderReferenceId providerReferenceId, ProviderRequest providerRequest);
}
