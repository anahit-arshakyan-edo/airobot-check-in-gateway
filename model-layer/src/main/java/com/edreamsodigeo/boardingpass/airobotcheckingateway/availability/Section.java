package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

public class Section {
    private String airline;
    private final Airport departure;
    private Airport arrival;

    public Section(String airline, String departure, String arrival) {
        this.airline = airline;
        this.departure = new Airport(departure);
        this.arrival = new Airport(arrival);
    }

    public boolean isValid() {
        if (! isPresent(airline)) {
            return false;
        }

        if (! arrival.isPresent()) {
            return false;
        }

        if (! departure.isPresent()) {
            return false;
        }

        return true;
    }

    private boolean isPresent(String value) {
        return value != null && ! value.isEmpty();
    }
}
