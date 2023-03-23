package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

public enum Status {
    SUCCESS("success"),
    FAILED("failed"),
    PENDING("pending");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
