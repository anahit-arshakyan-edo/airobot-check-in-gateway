package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityUseCase;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.ItineraryCheckInProviderResource;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.exception.GatewayException;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.spi.BadRequestException;

@Singleton
public class CheckInController implements ItineraryCheckInProviderResource {

    private final AvailabilityUseCase availabilityUseCase;

    @Inject
    public CheckInController(AvailabilityUseCase availabilityUseCase) {
        this.availabilityUseCase = availabilityUseCase;
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public CheckInAvailabilityResponse getCheckInAvailability(CheckInAvailabilityRequest checkInAvailabilityRequest) throws GatewayException, BadRequestException {
        Availability availability = availabilityUseCase.getAvailability(AvailabilityRequestMapper.INSTANCE.map(checkInAvailabilityRequest));
        return new AvailabilityResponseMapper().map(availability);
    }
}
