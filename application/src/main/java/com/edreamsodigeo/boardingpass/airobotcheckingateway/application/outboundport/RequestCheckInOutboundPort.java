package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;

public interface RequestCheckInOutboundPort {
    void send(ProviderReferenceId providerReferenceId, ProviderRequest providerRequest);
}
