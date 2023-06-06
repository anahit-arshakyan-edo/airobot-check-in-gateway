package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.delete;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.DeleteCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.RequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import datadog.trace.api.Trace;

@Singleton
public class DeleteCheckInAirobotOutboundAdapter implements DeleteCheckInOutboundPort {

    private final AirobotResource airobotResource;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private static final String TRACE_OPERATION_NAME = "airobot.request";

    @Inject
    public DeleteCheckInAirobotOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.airobotApiConfiguration = airobotApiConfiguration;
    }

    @Override
    @Trace(operationName = TRACE_OPERATION_NAME, resourceName = "deleteCheckIn")
    public void deleteCheckIn(RequestId requestId) {
        this.airobotResource.deleteCheckIn(airobotApiConfiguration.getApiToken(), requestId.valueString());
    }

}
