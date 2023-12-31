package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

public enum Gender {

    M("MALE"),
    F("FEMALE");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
