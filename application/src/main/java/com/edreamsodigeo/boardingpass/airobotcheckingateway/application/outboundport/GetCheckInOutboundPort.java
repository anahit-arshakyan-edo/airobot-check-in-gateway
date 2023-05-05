package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.ProviderRequestId;

import java.util.List;

public interface GetCheckInOutboundPort {
    List<ProviderRequestId> getProviderRequestIds(ItineraryCheckInId checkInId);
}
