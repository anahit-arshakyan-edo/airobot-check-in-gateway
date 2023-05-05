package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.request.CreateCheckInRequest;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RequestCheckInAirobotApiOutboundAdapter implements RequestCheckInOutboundPort {

    private final AirobotResource airobotResource;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private final CreateCheckInRequestMapper createCheckInRequestMapper;
    private final SegmentCheckInMapper segmentCheckInMapper;

    @Inject
    public RequestCheckInAirobotApiOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.airobotApiConfiguration = airobotApiConfiguration;
        this.createCheckInRequestMapper = new CreateCheckInRequestMapper();
        this.segmentCheckInMapper = new SegmentCheckInMapper();
    }

    @Override
    public SegmentCheckIn request(ProviderReferenceId providerReferenceId, SegmentCheckIn segmentCheckIn) {
        CreateCheckInRequest airobotCheckInRequest = createCheckInRequestMapper.map(segmentCheckIn, providerReferenceId.valueLong());
        CreateCheckInResponse airobotCheckInResponse = airobotResource.createCheckIn(airobotApiConfiguration.getApiToken(), airobotCheckInRequest);
        return segmentCheckInMapper.map(airobotCheckInResponse);
    }
}
