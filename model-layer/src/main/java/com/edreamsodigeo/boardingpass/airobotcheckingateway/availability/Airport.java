package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class Airport {
    private final String iataCode;

    public Airport(String iataCode) {
        this.iataCode = iataCode;
    }

    public boolean isPresent() {
        return iataCode != null && !iataCode.isEmpty();
    }
}

