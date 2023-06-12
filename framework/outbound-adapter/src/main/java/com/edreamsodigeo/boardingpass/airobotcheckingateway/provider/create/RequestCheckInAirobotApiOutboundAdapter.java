package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.exception.CreateCheckInAirobotException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.request.CreateCheckInRequest;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import datadog.trace.api.Trace;

@Singleton
public class RequestCheckInAirobotApiOutboundAdapter implements RequestCheckInOutboundPort {

    private final AirobotResource airobotResource;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private final CreateCheckInRequestMapper createCheckInRequestMapper;
    private final ProviderRequestMapper providerRequestMapper;
    private static final String TRACE_OPERATION_NAME = "airobot.request";

    @Inject
    public RequestCheckInAirobotApiOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.airobotApiConfiguration = airobotApiConfiguration;
        this.createCheckInRequestMapper = new CreateCheckInRequestMapper();
        this.providerRequestMapper = new ProviderRequestMapper();
    }

    @Override
    public void requestCheckIn(ProviderReferenceId providerReferenceId, ProviderRequest providerRequest) {
        CreateCheckInRequest airobotCheckInRequest = createCheckInRequestMapper.map(providerReferenceId, providerRequest);
        CreateCheckInResponse airobotCheckInResponse = sendCreateCheckInRequest(airobotCheckInRequest);
        providerRequestMapper.updateProviderRequest(providerRequest, airobotCheckInResponse);
    }

    @Trace(operationName = TRACE_OPERATION_NAME, resourceName = "sendCreateCheckInRequest")
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private CreateCheckInResponse sendCreateCheckInRequest(CreateCheckInRequest airobotCheckInRequest) {
        try {
            return airobotResource.createCheckIn(airobotApiConfiguration.getApiToken(), airobotCheckInRequest);
        } catch (Exception ex) {
            throw new CreateCheckInAirobotException(ex.getMessage(), ex);
        }
    }

}
