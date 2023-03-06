package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Passengers {

    public static final Passengers EMPTY_PASSENGERS = new Passengers(Collections.singletonList(""), 0, 0, 0);

    private final List<String> citizenships;
    private final int adults;
    private final int children;
    private final int infants;

    public Passengers(List<String> citizenships, int adults, int children, int infants) {
        this.citizenships = citizenships != null ? citizenships : new ArrayList<>();
        this.adults = adults;
        this.children = children;
        this.infants = infants;
    }

    public List<String> getCitizenships() {
        return citizenships;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getInfants() {
        return infants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passengers that = (Passengers) o;
        return adults == that.adults && children == that.children && infants == that.infants && citizenships.equals(that.citizenships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizenships, adults, children, infants);
    }

    @Override
    public String toString() {
        return "Passengers{"
                + "citizenships=" + citizenships
                + ", adults=" + adults
                + ", children=" + children
                + ", infants=" + infants
                + '}';
    }
}

