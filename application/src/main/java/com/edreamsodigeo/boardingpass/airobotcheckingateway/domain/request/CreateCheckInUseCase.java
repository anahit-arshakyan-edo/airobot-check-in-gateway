package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckIn;

public interface CreateCheckInUseCase {
    ItineraryCheckIn createCheckIn(ItineraryCheckIn itineraryCheckIn);
}
