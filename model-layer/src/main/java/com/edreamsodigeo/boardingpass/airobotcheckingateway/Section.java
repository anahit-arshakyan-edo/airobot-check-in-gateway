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

    public String airline() {
        return airline;
    }

    public String departure() {
        return departure;
    }

    public String arrival() {
        return arrival;
    }

    public boolean isValid() {
        if (! isPresent(airline)) {
            return false;
        }

        if (! isPresent(arrival)) {
            return false;
        }

        if (! isPresent(departure)) {
            return false;
        }

        return true;
    }

    private boolean isPresent(String value) {
        return value != null && ! value.isEmpty();
    }
}
