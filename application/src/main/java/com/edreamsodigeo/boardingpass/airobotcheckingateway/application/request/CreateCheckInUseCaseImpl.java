package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.DeleteCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GenerateReferenceIdOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.exception.StoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.exception.CreateCheckInAirobotException;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

import static com.edreams.configuration.ConfigurationEngine.injectMembers;

@Stateless
@Local(CreateCheckInUseCase.class)
public class CreateCheckInUseCaseImpl implements CreateCheckInUseCase {

    @Inject
    private RequestCheckInOutboundPort requestCheckInOutboundPort;
    @Inject
    private SaveCheckInOutboundPort saveItineraryCheckInOutboundPort;
    @Inject
    private GenerateReferenceIdOutboundPort generateReferenceIdOutboundPort;
    @Inject
    private DeleteCheckInOutboundPort deleteCheckInOutboundPort;

    @PostConstruct
    public void init() {
        injectMembers(this);
    }

    @Override
    public ItineraryCheckInId createCheckIn(ItineraryCheckIn itineraryCheckIn) {

        assignProviderReferenceId(itineraryCheckIn);
        sendCreateCheckInRequestsToProvider(itineraryCheckIn);
        storeItineraryCheckIn(itineraryCheckIn);

        return itineraryCheckIn.id();
    }

    private void assignProviderReferenceId(ItineraryCheckIn itineraryCheckIn) {
        ProviderReferenceId providerReferenceId = this.generateReferenceIdOutboundPort.generateReferenceId();
        itineraryCheckIn.assignReferenceId(providerReferenceId);
    }

    private void sendCreateCheckInRequestsToProvider(ItineraryCheckIn itineraryCheckIn) {
        List<ProviderRequest> sentProviderRequests = new ArrayList<>();

        for (ProviderRequest providerRequest : itineraryCheckIn.providerRequests()) {
            try {
                this.requestCheckInOutboundPort.requestCheckIn(itineraryCheckIn.referenceId(), providerRequest);
            } catch (CreateCheckInAirobotException ex) {
                sendDeleteCheckInRequestsToAirobot(sentProviderRequests);
                throw ex;
            }
            sentProviderRequests.add(providerRequest);
        }
    }

    private void sendDeleteCheckInRequestsToAirobot(List<ProviderRequest> providerRequests) {
        for (ProviderRequest providerRequest : providerRequests) {
            this.deleteCheckInOutboundPort.deleteCheckIn(providerRequest.requestId());
        }
    }

    private void storeItineraryCheckIn(ItineraryCheckIn itineraryCheckIn) {
        try {
            this.saveItineraryCheckInOutboundPort.save(itineraryCheckIn);
        } catch (StoreException ex) {
            sendDeleteCheckInRequestsToAirobot(itineraryCheckIn.providerRequests());
            throw ex;
        }
    }

}
