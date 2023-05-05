package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInStatusOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.response.CheckInStatusResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GetCheckInStatusAirobotApiOutboundAdapter implements GetCheckInStatusOutboundPort {

    private final AirobotResource airobotResource;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private final SegmentCheckInMapper segmentCheckInMapper;

    @Inject
    public GetCheckInStatusAirobotApiOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.airobotApiConfiguration = airobotApiConfiguration;
        this.segmentCheckInMapper = new SegmentCheckInMapper();
    }


    @Override
    public SegmentCheckIn getStatus(ProviderRequestId requestId) {
        CheckInStatusResponse checkInStatusResponse = this.airobotResource.getCheckInStatus(airobotApiConfiguration.getApiToken(), requestId.valueString());
        return segmentCheckInMapper.map(checkInStatusResponse);
    }
}
