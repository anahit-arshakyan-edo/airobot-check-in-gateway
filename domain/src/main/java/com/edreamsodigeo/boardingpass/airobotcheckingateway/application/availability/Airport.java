package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.Objects;

public class Airport {
    private final String iataCode;

    public Airport(String iataCode) {
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
        return "Airport{"
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
        Airport airport = (Airport) o;
        return Objects.equals(iataCode, airport.iataCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iataCode);
    }
}

