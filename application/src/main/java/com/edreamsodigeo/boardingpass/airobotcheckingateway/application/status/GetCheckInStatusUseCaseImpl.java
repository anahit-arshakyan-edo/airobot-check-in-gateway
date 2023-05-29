package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInStatusOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.RequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
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

        List<ProviderRequestMetadata> providerRequestsMetadata = getProviderRequestsMetadata(itineraryCheckInId);
        ItineraryCheckIn itineraryCheckIn = buildItineraryCheckInStatus(itineraryCheckInId, providerRequestsMetadata);
        assignProviderReferenceId(itineraryCheckIn);

        return itineraryCheckIn;
    }

    private List<ProviderRequestMetadata> getProviderRequestsMetadata(ItineraryCheckInId itineraryCheckInId) {
        List<ProviderRequestMetadata> providerRequestsMetadata = this.getCheckInOutboundPort.getProviderRequestsMetadata(itineraryCheckInId);
        if (providerRequestsMetadata.isEmpty()) {
            throw new ItineraryCheckInNotFoundException("No Provider Requests belonging to this CheckIn ID have been found");
        } else {
            return providerRequestsMetadata;
        }
    }

    private ItineraryCheckIn buildItineraryCheckInStatus(ItineraryCheckInId itineraryCheckInId, List<ProviderRequestMetadata> providerRequestsMetadata) {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();

        for (ProviderRequestMetadata providerRequestMetadata : providerRequestsMetadata) {
            List<BoardingPassMetadata> boardingPassesMetadata = getBoardingPassesMetadata(providerRequestMetadata.providerRequestId());
            SegmentCheckIn segmentCheckIn = getSegmentCheckInStatus(providerRequestMetadata.requestId());
            BoardingPassIdMapper.fillBoardingPassesWithId(boardingPassesMetadata, segmentCheckIn.boardingPasses());
            segmentCheckIns.add(segmentCheckIn);
        }

        return ItineraryCheckIn.from(itineraryCheckInId, ProviderReferenceId.notAssigned(), segmentCheckIns);
    }

    private List<BoardingPassMetadata> getBoardingPassesMetadata(ProviderRequestId providerRequestId) {
        return this.getCheckInOutboundPort.getBoardingPassesMetadata(providerRequestId);
    }

    private SegmentCheckIn getSegmentCheckInStatus(RequestId requestId) {
        return this.getCheckInStatusOutboundPort.getStatus(requestId);
    }

    private void assignProviderReferenceId(ItineraryCheckIn itineraryCheckIn) {
        ItineraryCheckInMetadata itineraryCheckInMetadata = this.getCheckInOutboundPort.getItineraryCheckInMetadata(itineraryCheckIn.id());
        itineraryCheckIn.assignReferenceId(itineraryCheckInMetadata.referenceId());
    }

}
