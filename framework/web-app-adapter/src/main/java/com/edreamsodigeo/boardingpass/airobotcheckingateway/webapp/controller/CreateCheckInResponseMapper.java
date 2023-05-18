package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CreateCheckInResponse;

public class CreateCheckInResponseMapper {

    public CreateCheckInResponse map(ItineraryCheckInId itineraryCheckInId) {
        return CreateCheckInResponse.builder()
                .withId(itineraryCheckInId.value())
                .build();
    }

}
