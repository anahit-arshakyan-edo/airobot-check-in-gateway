package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.exception;

public class CreateCheckInAirobotException extends RuntimeException {

    public CreateCheckInAirobotException(String message) {
        super(message);
    }

    public CreateCheckInAirobotException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
