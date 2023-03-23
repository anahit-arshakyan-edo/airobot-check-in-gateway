package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

public enum Format {
    PDF("pdf"),
    PASSKIT("passkit");

    private final String description;

    Format(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
