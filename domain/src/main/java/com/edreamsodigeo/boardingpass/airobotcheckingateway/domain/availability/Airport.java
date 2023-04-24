package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.validation.Checker;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public final class Airport {
    @NotBlank
    private final String iataCode;

    private Airport(String iataCode) {
        this.iataCode = iataCode;
    }

    public static Airport create(String iataCode) {
        Airport result = new Airport(iataCode);
        Checker.checkValidityWithJSR380(result);
        return result;
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

