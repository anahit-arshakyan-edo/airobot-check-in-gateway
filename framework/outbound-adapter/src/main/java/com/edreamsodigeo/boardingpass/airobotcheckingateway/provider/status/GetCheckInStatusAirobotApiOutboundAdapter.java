package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.RequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInStatusOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.response.CheckInStatusResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import datadog.trace.api.Trace;

@Singleton
public class GetCheckInStatusAirobotApiOutboundAdapter implements GetCheckInStatusOutboundPort {

    private final AirobotResource airobotResource;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private final SegmentCheckInMapper segmentCheckInMapper;
    private static final String TRACE_OPERATION_NAME = "airobot.request";

    @Inject
    public GetCheckInStatusAirobotApiOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.airobotApiConfiguration = airobotApiConfiguration;
        this.segmentCheckInMapper = new SegmentCheckInMapper();
    }


    @Override
    public SegmentCheckIn getStatus(RequestId requestId) {
        CheckInStatusResponse checkInStatusResponse = sendCheckInStatusRequest(requestId.valueString());
        return segmentCheckInMapper.map(checkInStatusResponse);
    }

    @Trace(operationName = TRACE_OPERATION_NAME, resourceName = "sendCheckInStatusRequest")
    private CheckInStatusResponse sendCheckInStatusRequest(String requestId) {
        return this.airobotResource.getCheckInStatus(airobotApiConfiguration.getApiToken(), requestId);
    }
}
