package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.Collections;
import java.util.List;

public class Passengers {

    public static final Passengers EMPTY_PASSENGERS = new Passengers(Collections.singletonList(""), 0, 0, 0);

    private final List<String> citizenships;
    private final int adults;
    private final int children;
    private final int infants;

    public Passengers(List<String> citizenships, int adults, int children, int infants) {
        this.citizenships = citizenships;
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
}

