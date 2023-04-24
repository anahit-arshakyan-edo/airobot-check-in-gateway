package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckIn;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class CreateCheckInUseCaseImpl implements CreateCheckInUseCase {

    private final RequestCheckInOutboundPort requestCheckInOutboundPort;
    private final SaveCheckInOutboundPort saveCheckInOutboundPort;

    @Inject
    public CreateCheckInUseCaseImpl(RequestCheckInOutboundPort requestCheckInOutboundPort, SaveCheckInOutboundPort saveCheckInOutboundPort) {
        this.requestCheckInOutboundPort = requestCheckInOutboundPort;
        this.saveCheckInOutboundPort = saveCheckInOutboundPort;
    }

    @Override
    @Transactional
    public ItineraryCheckIn createCheckIn(ItineraryCheckIn itineraryCheckInToBeRequested) {
        ItineraryCheckIn requestedItineraryCheckIn = requestCheckInOutboundPort.request(itineraryCheckInToBeRequested);
        saveCheckInOutboundPort.save(requestedItineraryCheckIn);
        return requestedItineraryCheckIn;
    }

}
