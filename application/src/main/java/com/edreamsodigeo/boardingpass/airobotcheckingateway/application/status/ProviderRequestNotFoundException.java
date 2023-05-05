package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

public class ProviderRequestNotFoundException extends RuntimeException {

    public ProviderRequestNotFoundException(String message) {
        super(message);
    }

    public ProviderRequestNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
