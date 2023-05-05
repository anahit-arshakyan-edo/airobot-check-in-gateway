package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

public enum DocumentType {

    PASSPORT("passport"),
    NATIONAL_ID("national_id");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

}
