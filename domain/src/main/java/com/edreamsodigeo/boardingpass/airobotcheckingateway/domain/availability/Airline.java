package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.validation.Checker;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public final class Airline {
    @NotBlank
    private final String iataCode;

    private Airline(String iataCode) {
        this.iataCode = iataCode;
    }

    public static Airline create(String iataCode) {
        Airline result = new Airline(iataCode);
        Checker.checkValidityWithJSR380(result);
        return result;
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
