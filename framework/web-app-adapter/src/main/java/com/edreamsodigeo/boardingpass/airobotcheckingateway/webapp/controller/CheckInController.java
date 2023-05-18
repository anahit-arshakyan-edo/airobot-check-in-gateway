package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status.GetCheckInStatusUseCase;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.ItineraryCheckInProviderResource;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.exception.GatewayException;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.getcheckinstatus.CheckInStatusResponse;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CreateCheckInResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.spi.BadRequestException;

@Singleton
public class CheckInController implements ItineraryCheckInProviderResource {
    private final GetAvailabilityUseCase availabilityUseCase;
    private final AvailabilityRequestMapper requestMapper = new AvailabilityRequestMapper();
    private final CreateCheckInUseCase createCheckInUseCase;
    private final ItineraryCheckInMapper itineraryCheckInMapper = new ItineraryCheckInMapper();
    private final CreateCheckInResponseMapper createCheckInResponseMapper = new CreateCheckInResponseMapper();
    private final GetCheckInStatusUseCase getCheckInStatusUseCase;
    private final CheckInStatusResponseMapper checkInStatusResponseMapper = new CheckInStatusResponseMapper();

    @Inject
    public CheckInController(GetAvailabilityUseCase availabilityUseCase, CreateCheckInUseCase createCheckInUseCase, GetCheckInStatusUseCase getCheckInStatusUseCase) {
        this.availabilityUseCase = availabilityUseCase;
        this.createCheckInUseCase = createCheckInUseCase;
        this.getCheckInStatusUseCase = getCheckInStatusUseCase;
    }

    // do we still need this comment? Can we remove it?
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public CheckInAvailabilityResponse getCheckInAvailability(CheckInAvailabilityRequest checkInAvailabilityRequest) {
        AvailabilityRequest request = requestMapper.map(checkInAvailabilityRequest);
        Availability availability = availabilityUseCase.getAvailability(request);
        return new AvailabilityResponseMapper().map(availability);
    }

    @Override
    public CreateCheckInResponse createCheckIn(CreateCheckInRequest createCheckInRequest) throws GatewayException, BadRequestException {
        ItineraryCheckInId itineraryCheckInId = createCheckInUseCase.createCheckIn(itineraryCheckInMapper.map(createCheckInRequest));
        return createCheckInResponseMapper.map(itineraryCheckInId);
    }

    @Override
    public CheckInStatusResponse getCheckInStatus(String checkInId) throws GatewayException, BadRequestException {
        ItineraryCheckIn itineraryCheckIn = this.getCheckInStatusUseCase.getStatus(ItineraryCheckInId.from(checkInId));
        return checkInStatusResponseMapper.map(itineraryCheckIn);
    }

}
