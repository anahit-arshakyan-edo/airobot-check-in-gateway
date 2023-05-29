package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInMetadata;

import java.util.List;

public interface GetCheckInOutboundPort {
    List<ProviderRequestMetadata> getProviderRequestsMetadata(ItineraryCheckInId checkInId);
    List<BoardingPassMetadata> getBoardingPassesMetadata(ProviderRequestId providerRequestId);
    ItineraryCheckInMetadata getItineraryCheckInMetadata(ItineraryCheckInId itineraryCheckInId);
}
