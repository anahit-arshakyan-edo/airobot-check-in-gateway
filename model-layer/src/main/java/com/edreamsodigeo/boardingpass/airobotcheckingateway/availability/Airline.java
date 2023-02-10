package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class Airline {
    private final String iataCode;

    public Airline(String iataCode) {
        this.iataCode = iataCode;
    }

    public boolean isPresent() {
        return iataCode != null && !iataCode.isEmpty();
    }

}
