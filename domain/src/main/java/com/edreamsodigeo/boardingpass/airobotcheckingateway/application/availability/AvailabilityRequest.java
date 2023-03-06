package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AvailabilityRequest {
    @NotEmpty(message = "availability request should have at least one section")
    private final List<@NotNull @Valid Section> sections;

    @NotNull
    private final Passengers passengers;

    AvailabilityRequest(List<Section> sections, Passengers passengers) {
        this.sections = sections;
        this.passengers = passengers;
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Passengers getPassengers() {
        return passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvailabilityRequest that = (AvailabilityRequest) o;
        return Objects.equals(sections, that.sections) && Objects.equals(passengers, that.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sections, passengers);
    }

    @Override
    public String toString() {
        return "AvailabilityRequest{"
                + "sections=" + sections
                + ", passengers=" + passengers
                + '}';
    }
}
