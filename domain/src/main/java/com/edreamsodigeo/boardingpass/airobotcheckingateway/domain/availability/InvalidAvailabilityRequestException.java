package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

public class InvalidAvailabilityRequestException extends RuntimeException {
    public InvalidAvailabilityRequestException() {
    }

    public InvalidAvailabilityRequestException(String message) {
        super(message);
    }
}
