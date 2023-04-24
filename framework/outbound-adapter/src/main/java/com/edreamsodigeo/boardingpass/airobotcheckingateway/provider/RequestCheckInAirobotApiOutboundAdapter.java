package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.request.CreateCheckInRequest;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

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
    public ItineraryCheckIn request(ItineraryCheckIn itineraryCheckIn) {

        List<SegmentCheckIn> requestedSegmentCheckIns = new ArrayList<>();

        for (SegmentCheckIn segmentCheckIn : itineraryCheckIn.segmentCheckIns()) {
            CreateCheckInRequest airobotCheckInRequest = createCheckInRequestMapper.from(segmentCheckIn, itineraryCheckIn.referenceId());
            CreateCheckInResponse airobotCheckInResponse = airobotResource.createCheckIn(airobotApiConfiguration.getApiToken(), airobotCheckInRequest);
            requestedSegmentCheckIns.add(segmentCheckInMapper.from(airobotCheckInResponse));
        }

        return new ItineraryCheckIn(
                itineraryCheckIn.id(),
                requestedSegmentCheckIns
        );
    }
}
