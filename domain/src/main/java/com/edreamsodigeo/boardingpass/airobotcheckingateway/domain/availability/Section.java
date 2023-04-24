package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Section {
    @NotNull @Valid
    private final Airline airline;

    @NotNull @Valid
    private final Airport departure;

    @NotNull @Valid
    private final Airport arrival;

    Section(Airline airline, Airport departure, Airport arrival) {
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
    }

    public String airlineIataCode() {
        return airline.iataCode();
    }

    public String arrivalAirportIataCode() {
        return arrival.iataCode();
    }

    public String departureAirportIataCode() {
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

