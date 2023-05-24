package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;

import java.util.List;

public interface GetCheckInOutboundPort {
    List<ProviderRequestId> getProviderRequestIds(ItineraryCheckInId checkInId);
}
