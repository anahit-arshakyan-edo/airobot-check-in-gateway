package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

public class ItineraryCheckInNotFoundException extends RuntimeException {

    public ItineraryCheckInNotFoundException(String message) {
        super(message);
    }

    public ItineraryCheckInNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
