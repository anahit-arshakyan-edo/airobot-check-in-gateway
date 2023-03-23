package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.exception;

public class StoreException extends RuntimeException {

    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
