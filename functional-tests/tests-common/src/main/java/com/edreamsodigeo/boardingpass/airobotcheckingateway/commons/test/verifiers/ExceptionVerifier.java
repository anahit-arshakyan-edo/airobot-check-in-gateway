package com.edreamsodigeo.boardingpass.airobotcheckingateway.commons.test.verifiers;

import org.testng.Assert;

public class ExceptionVerifier {
    private final String exception;
    private final String cause;
    private final String message;

    public ExceptionVerifier(String exception, String cause, String message) {
        this.exception = exception;
        this.cause = cause;
        this.message = message;
    }

    public void verify(Exception exception) {
        if (this.exception != null) {
            Assert.assertEquals(exception.getClass().getName(), this.exception);
        }
        if (this.message != null) {
            Assert.assertEquals(exception.getMessage(), this.message);
        }
        if (this.cause != null) {
            Assert.assertEquals(exception.getCause().getClass().getSimpleName(), this.cause);
        }
    }
}
