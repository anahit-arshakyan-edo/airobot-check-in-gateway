package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

public interface AvailabilityUseCase {
    Availability getAvailability(AvailabilityRequest availabilityRequest);
}
