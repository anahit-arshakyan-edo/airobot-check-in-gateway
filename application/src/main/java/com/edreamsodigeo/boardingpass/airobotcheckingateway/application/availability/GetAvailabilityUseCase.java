package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

public interface GetAvailabilityUseCase {
    Availability getAvailability(AvailabilityRequest availabilityRequest);
}
