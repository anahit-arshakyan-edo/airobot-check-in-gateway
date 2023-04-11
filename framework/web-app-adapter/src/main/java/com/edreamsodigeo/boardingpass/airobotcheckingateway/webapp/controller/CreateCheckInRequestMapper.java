package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckInId;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;

import java.util.Collections;

public class CreateCheckInRequestMapper {

    CheckIn map(CreateCheckInRequest createCheckInRequestDto) {
        return new CheckIn(CheckInId.create(), -1, Collections.emptyList());
    }

}
