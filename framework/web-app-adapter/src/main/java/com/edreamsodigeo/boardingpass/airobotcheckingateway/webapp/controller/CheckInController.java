package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.ItineraryCheckInProviderResource;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CheckInController implements ItineraryCheckInProviderResource {
    private final GetAvailabilityUseCase availabilityUseCase;
    private final AvailabilityRequestMapper requestMapper = new AvailabilityRequestMapper();

    @Inject
    public CheckInController(GetAvailabilityUseCase availabilityUseCase) {
        this.availabilityUseCase = availabilityUseCase;
    }

    // do we still need this comment? Can we remove it?
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public CheckInAvailabilityResponse getCheckInAvailability(CheckInAvailabilityRequest checkInAvailabilityRequest) {
        AvailabilityRequest request = requestMapper.map(checkInAvailabilityRequest);
        Availability availability = availabilityUseCase.getAvailability(request);
        return new AvailabilityResponseMapper().map(availability);
    }
}
