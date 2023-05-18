package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.exception;

public class RetrieveException extends RuntimeException {

    public RetrieveException(String message) {
        super(message);
    }

    public RetrieveException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
