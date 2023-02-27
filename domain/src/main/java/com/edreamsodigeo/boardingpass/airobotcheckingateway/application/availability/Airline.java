package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.Objects;

public class Airline {
    private final String iataCode;

    public Airline(String iataCode) {
        this.iataCode = iataCode;
    }

    public boolean isPresent() {
        return iataCode != null && !iataCode.isEmpty();
    }

    public String iataCode() {
        return iataCode;
    }

    @Override
    public String toString() {
        return "Airline{"
                + "iataCode='" + iataCode + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Airline airline = (Airline) o;
        return Objects.equals(iataCode, airline.iataCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iataCode);
    }
}
