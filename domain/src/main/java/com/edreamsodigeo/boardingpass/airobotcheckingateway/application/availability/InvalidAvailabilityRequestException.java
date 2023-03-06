package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

public class InvalidAvailabilityRequestException extends RuntimeException {
    public InvalidAvailabilityRequestException() {
    }

    public InvalidAvailabilityRequestException(String message) {
        super(message);
    }
}
