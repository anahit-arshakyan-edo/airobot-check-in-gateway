package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityRequest {

    private String departureAirport;
    private Section section;

    public AvailabilityRequest(String departureAirport) {
        this.departureAirport = departureAirport;
        this.section = new Section(null, departureAirport, null);
    }

    public AvailabilityRequest(Section section) {
        this.section = section;
        this.departureAirport = section.departure();
    }


    public String getDepartureAirport() {
        return departureAirport;
    }
}
