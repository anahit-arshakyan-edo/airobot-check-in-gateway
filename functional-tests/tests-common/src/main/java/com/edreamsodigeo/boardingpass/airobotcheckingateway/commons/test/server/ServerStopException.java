package com.edreamsodigeo.boardingpass.airobotcheckingateway.commons.test.server;

public class ServerStopException extends Exception {
    public ServerStopException(String message) {
        super(message);
    }

    public ServerStopException(String message, Throwable cause) {
        super(message, cause);
    }
}
