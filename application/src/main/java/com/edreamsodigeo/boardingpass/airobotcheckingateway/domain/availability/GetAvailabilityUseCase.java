package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

public interface GetAvailabilityUseCase {
    Availability getAvailability(AvailabilityRequest availabilityRequest);
}
