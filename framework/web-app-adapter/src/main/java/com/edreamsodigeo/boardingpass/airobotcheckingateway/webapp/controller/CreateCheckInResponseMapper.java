package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CreateCheckInResponse;

public class CreateCheckInResponseMapper {

    public CreateCheckInResponse map(ItineraryCheckIn itineraryCheckIn) {
        return CreateCheckInResponse.builder()
                .withId(itineraryCheckIn.id().value())
                .build();
    }

}
