package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.request.CreateCheckInRequest;

public class CreateCheckInRequestMapper {
    public CreateCheckInRequest map(CheckIn checkIn) {
        return CreateCheckInRequest.builder()
                .build();
    }
}
