package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckIn;

public interface RequestCheckInOutboundPort {
    ItineraryCheckIn request(ItineraryCheckIn itineraryCheckIn);
}
