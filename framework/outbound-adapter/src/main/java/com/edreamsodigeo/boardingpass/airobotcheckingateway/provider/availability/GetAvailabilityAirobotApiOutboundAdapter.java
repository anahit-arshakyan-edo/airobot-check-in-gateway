package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import datadog.trace.api.Trace;

@Singleton
public class GetAvailabilityAirobotApiOutboundAdapter implements GetAvailabilityOutboundPort {

    private final AirobotResource airobotResource;
    private final AvailabilityRequestMapper requestMapper;
    private final AvailabilityResponseMapper responseMapper;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private static final String TRACE_OPERATION_NAME = "airobot.request";

    @Inject
    public GetAvailabilityAirobotApiOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.requestMapper = new AvailabilityRequestMapper();
        this.responseMapper = new AvailabilityResponseMapper();
        this.airobotApiConfiguration = airobotApiConfiguration;
    }

    @Override
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.request.AvailabilityRequest requestDto =
                requestMapper.map(availabilityRequest);

        AvailabilityResponse response = sendAvailabilityRequest(requestDto);

        return responseMapper.map(response);
    }

    @Trace(operationName = TRACE_OPERATION_NAME, resourceName = "sendAvailabilityRequest")
    private AvailabilityResponse sendAvailabilityRequest(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.request.AvailabilityRequest requestDto) {
        return airobotResource.getFlightAvailability(airobotApiConfiguration.getApiToken(), requestDto);
    }

}
