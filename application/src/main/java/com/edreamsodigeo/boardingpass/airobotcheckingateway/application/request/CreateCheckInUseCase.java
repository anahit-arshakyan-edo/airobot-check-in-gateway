package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;

public interface CreateCheckInUseCase {
    ItineraryCheckIn createCheckIn(ItineraryCheckIn itineraryCheckIn);
}
