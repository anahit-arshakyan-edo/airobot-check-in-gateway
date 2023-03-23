package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.response.AvailabilityResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AirobotOutboundAdapter implements AirobotOutboundPort {

    private final AirobotResource airobotResource;
    private final AvailabilityRequestMapper requestMapper;
    private final AvailabilityResponseMapper responseMapper;
    private final AirobotApiConfiguration airobotApiConfiguration;

    @Inject
    public AirobotOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.requestMapper = new AvailabilityRequestMapper();
        this.responseMapper = new AvailabilityResponseMapper();
        this.airobotApiConfiguration = airobotApiConfiguration;
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.request.AvailabilityRequest requestDto =
                requestMapper.map(availabilityRequest);

        AvailabilityResponse response = airobotResource.getFlightAvailability(airobotApiConfiguration.getApiToken(), requestDto);

        return responseMapper.map(response);
    }

}
