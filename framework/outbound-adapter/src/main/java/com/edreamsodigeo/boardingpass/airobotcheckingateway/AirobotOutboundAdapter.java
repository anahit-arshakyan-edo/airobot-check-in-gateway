package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.response.AvailabilityResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AirobotOutboundAdapter implements AirobotOutboundPort {

    private static final String API_TOKEN = "oPkvZwp0wHIdBJWeHgU0r7TsqWUO7CMar9RkW9nVPEIG2c9bK19NYgGg4f9v";
    private final AirobotResource airobotResource;
    private final AvailabilityRequestMapper requestMapper;
    private final AvailabilityResponseMapper responseMapper;

    @Inject
    public AirobotOutboundAdapter(AirobotResource airobotResource) {
        this.airobotResource = airobotResource;
        this.requestMapper = new AvailabilityRequestMapper();
        this.responseMapper = new AvailabilityResponseMapper();
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.request.AvailabilityRequest requestDto =
                requestMapper.map(availabilityRequest);

        AvailabilityResponse response = airobotResource.getFlightAvailability(API_TOKEN, requestDto);

        return responseMapper.map(response);
    }

}
