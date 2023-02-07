package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class Section {
    private final Airline airline;
    private final Airport departure;
    private final Airport arrival;

    public Section(Airline airline, Airport departure, Airport arrival) {
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
    }

    public boolean isValid() {
        return airline.isPresent() &&
                departure.isPresent() &&
                arrival.isPresent();
    }
}

