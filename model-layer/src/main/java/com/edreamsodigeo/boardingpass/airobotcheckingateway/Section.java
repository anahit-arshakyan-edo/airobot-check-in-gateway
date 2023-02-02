package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class Section {
    private String airline;
    private String departure;
    private String arrival;

    public Section(String airline, String departure, String arrival) {
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getAirline() {
        return airline;
    }

    public String departure() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }
}
