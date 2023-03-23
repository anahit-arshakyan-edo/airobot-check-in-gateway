package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

public enum SeatPreference {

    WINDOW("window"),
    MIDDLE("middle"),
    AISLE("aisle");

    private final String description;

    SeatPreference(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

}
