package com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration;

public class InvalidConfigurationException extends RuntimeException {
    public InvalidConfigurationException(String message, Exception e) {
        super(message, e);
    }
}
