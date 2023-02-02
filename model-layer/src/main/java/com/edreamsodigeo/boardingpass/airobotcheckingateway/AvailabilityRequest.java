package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityRequest {

    private String departureAirport;

    public AvailabilityRequest(String departureAirport) {
        this.departureAirport = departureAirport;
    }


    public String getDepartureAirport() {
        return departureAirport;
    }
}
