package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
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
    private SaveCheckInOutboundPort saveCheckInOutboundPort;

    @PostConstruct
    public void init() {
        injectMembers(this);
    }

    @Override
    public ItineraryCheckIn createCheckIn(ItineraryCheckIn itineraryCheckInToBeRequested) {

        List<SegmentCheckIn> requestedSegmentCheckIns = new ArrayList<>();

        for (SegmentCheckIn segmentCheckInToBeRequested : itineraryCheckInToBeRequested.segmentCheckIns()) {
            SegmentCheckIn requestedSegmentCheckIn = requestCheckInOutboundPort.request(itineraryCheckInToBeRequested.referenceId(), segmentCheckInToBeRequested);
            requestedSegmentCheckIns.add(requestedSegmentCheckIn);
        }

        ItineraryCheckIn requestedItineraryCheckIn = new ItineraryCheckIn(
                itineraryCheckInToBeRequested.id(),
                requestedSegmentCheckIns
        );

        saveCheckInOutboundPort.save(requestedItineraryCheckIn);
        return requestedItineraryCheckIn;
    }

}
