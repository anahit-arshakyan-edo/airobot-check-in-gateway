package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.google.inject.Inject;

public class RequestCheckInAirobotOutboundAdapter implements RequestCheckInOutboundPort {

    private final AirobotResource airobotResource;
    private final AirobotApiConfiguration airobotApiConfiguration;
    private final CreateCheckInRequestMapper createCheckInRequestMapper;
    private final CreateCheckInResponseMapper createCheckInResponseMapper;

    @Inject
    public RequestCheckInAirobotOutboundAdapter(AirobotResource airobotResource, AirobotApiConfiguration airobotApiConfiguration) {
        this.airobotResource = airobotResource;
        this.airobotApiConfiguration = airobotApiConfiguration;
        this.createCheckInRequestMapper = new CreateCheckInRequestMapper();
        this.createCheckInResponseMapper = new CreateCheckInResponseMapper();
    }

    @Override
    public CheckIn request(CheckIn checkIn) {
        return createCheckInResponseMapper.map(checkIn.id(), airobotResource.createCheckIn(airobotApiConfiguration.getApiToken(), createCheckInRequestMapper.map(checkIn)));
    }

}
