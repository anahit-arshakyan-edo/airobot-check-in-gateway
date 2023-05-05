package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;

public interface GetCheckInStatusUseCase {
    ItineraryCheckIn getStatus(ItineraryCheckInId itineraryCheckInId);
}
