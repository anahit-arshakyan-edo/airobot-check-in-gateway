package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GetAvailabilityAirobotOutboundAdapter implements GetAvailabilityOutboundPort {

    private final AirobotResource airobotResource;
    private final AvailabilityRequestMapper requestMapper;
    private final AvailabilityResponseMapper responseMapper;
    private final AirobotApiConfiguration airobotApiConfiguration;

    @Inject
    public GetAvailabilityAirobotOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.requestMapper = new AvailabilityRequestMapper();
        this.responseMapper = new AvailabilityResponseMapper();
        this.airobotApiConfiguration = airobotApiConfiguration;
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.request.AvailabilityRequest requestDto =
                requestMapper.map(availabilityRequest);

        AvailabilityResponse response = airobotResource.getFlightAvailability(airobotApiConfiguration.getApiToken(), requestDto);

        return responseMapper.map(response);
    }

}
