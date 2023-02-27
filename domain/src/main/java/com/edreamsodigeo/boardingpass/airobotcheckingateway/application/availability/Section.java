package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.Objects;

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
        return airline.isPresent()
                && departure.isPresent()
                && arrival.isPresent();
    }

    public String getAirlineIataCode() {
        return airline.iataCode();
    }

    public String getArrivalAirportIataCode() {
        return arrival.iataCode();
    }

    public String getDepartureAirportIataCode() {
        return departure.iataCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        return airline.equals(section.airline) && departure.equals(section.departure) && arrival.equals(section.arrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airline, departure, arrival);
    }

    @Override
    public String toString() {
        return "Section{"
                + "airline=" + airline
                + ", departure=" + departure
                + ", arrival=" + arrival
                + '}';
    }
}

