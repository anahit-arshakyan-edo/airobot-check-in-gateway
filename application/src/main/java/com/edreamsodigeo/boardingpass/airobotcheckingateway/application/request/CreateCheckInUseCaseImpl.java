package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import static com.edreams.configuration.ConfigurationEngine.injectMembers;

@Stateless
@Local(CreateCheckInUseCase.class)
public class CreateCheckInUseCaseImpl implements CreateCheckInUseCase {

    @Inject
    private RequestCheckInOutboundPort requestCheckInOutboundPort;
    @Inject
    private SaveCheckInOutboundPort saveItineraryCheckInOutboundPort;

    @PostConstruct
    public void init() {
        injectMembers(this);
    }

    @Override
    public ItineraryCheckInId createCheckIn(ItineraryCheckIn itineraryCheckIn) {

        sendAndUpdateCheckInRequests(itineraryCheckIn);
        storeItineraryCheckIn(itineraryCheckIn);

        return itineraryCheckIn.id();
    }

    private void sendAndUpdateCheckInRequests(ItineraryCheckIn itineraryCheckIn) {
        for (ProviderRequest providerRequest : itineraryCheckIn.providerRequests()) {
            requestCheckInOutboundPort.send(itineraryCheckIn.referenceId(), providerRequest);
        }
    }

    private void storeItineraryCheckIn(ItineraryCheckIn itineraryCheckIn) {
        saveItineraryCheckInOutboundPort.save(itineraryCheckIn);
    }

}
