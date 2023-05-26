package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInStatusOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

import static com.edreams.configuration.ConfigurationEngine.injectMembers;

@Stateless
@Local(GetCheckInStatusUseCase.class)
public class GetCheckInStatusUseCaseImpl implements GetCheckInStatusUseCase {

    @Inject
    private GetCheckInOutboundPort getCheckInOutboundPort;
    @Inject
    private GetCheckInStatusOutboundPort getCheckInStatusOutboundPort;

    @PostConstruct
    public void init() {
        injectMembers(this);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ItineraryCheckIn getStatus(ItineraryCheckInId itineraryCheckInId) {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        List<ProviderRequestMetadata> providerRequestsMetadata = this.getCheckInOutboundPort.getProviderRequestsMetadata(itineraryCheckInId);

        if (providerRequestsMetadata.isEmpty()) {
            throw new ItineraryCheckInNotFoundException("No Provider Requests belonging to this CheckIn ID have been found");
        }

        for (ProviderRequestMetadata providerRequestMetadata : providerRequestsMetadata) {
            List<BoardingPassMetadata> boardingPassesMetadata = this.getCheckInOutboundPort.getBoardingPassesMetadata(providerRequestMetadata.providerRequestId());
            SegmentCheckIn segmentCheckIn = this.getCheckInStatusOutboundPort.getStatus(providerRequestMetadata.requestId());
            BoardingPassIdMapper.fillBoardingPassesWithId(boardingPassesMetadata, segmentCheckIn.boardingPasses());
            segmentCheckIns.add(segmentCheckIn);
        }

        return new ItineraryCheckIn(
                itineraryCheckInId,
                segmentCheckIns
        );
    }

}
