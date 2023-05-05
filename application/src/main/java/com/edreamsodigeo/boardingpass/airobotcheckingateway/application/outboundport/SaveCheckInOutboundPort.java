package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;

public interface SaveCheckInOutboundPort {
    void save(ItineraryCheckIn itineraryCheckIn);
}
