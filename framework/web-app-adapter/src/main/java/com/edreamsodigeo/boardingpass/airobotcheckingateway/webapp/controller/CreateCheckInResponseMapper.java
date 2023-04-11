package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CreateCheckinResponse;

public class CreateCheckInResponseMapper {

    public CreateCheckinResponse map(CheckIn checkIn) {
        return CreateCheckinResponse.builder()
                .build();
    }

}
