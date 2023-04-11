package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCase;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.ItineraryCheckInProviderResource;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.exception.GatewayException;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CreateCheckinResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.spi.BadRequestException;

@Singleton
public class CheckInController implements ItineraryCheckInProviderResource {
    private final GetAvailabilityUseCase availabilityUseCase;
    private final AvailabilityRequestMapper requestMapper = new AvailabilityRequestMapper();
    private final CreateCheckInUseCase createCheckInUseCase;
    private final CreateCheckInRequestMapper createCheckInRequestMapper = new CreateCheckInRequestMapper();
    private final CreateCheckInResponseMapper createCheckInResponseMapper = new CreateCheckInResponseMapper();

    @Inject
    public CheckInController(GetAvailabilityUseCase availabilityUseCase, CreateCheckInUseCase createCheckInUseCase) {
        this.availabilityUseCase = availabilityUseCase;
        this.createCheckInUseCase = createCheckInUseCase;
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
    public CreateCheckinResponse createCheckIn(CreateCheckInRequest createCheckInRequest) throws GatewayException, BadRequestException {
        return createCheckInResponseMapper.map(createCheckInUseCase.createCheckIn(createCheckInRequestMapper.map(createCheckInRequest)));
    }
}
