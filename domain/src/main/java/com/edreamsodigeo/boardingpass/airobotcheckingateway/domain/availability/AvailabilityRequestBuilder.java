package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.validation.Checker;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityRequestBuilder {
    private List<Section> sectionList;
    private Passengers passengers;

    public AvailabilityRequestBuilder() {
        this.sectionList = new ArrayList<>();
        this.passengers = Passengers.EMPTY_PASSENGERS;
    }

    public AvailabilityRequestBuilder withSections(List<Section> sectionList) {
        this.sectionList = sectionList != null ? sectionList : new ArrayList<>();
        return this;
    }

    public AvailabilityRequestBuilder withSection(Section section) {
        sectionList.add(section);
        return this;
    }

    public AvailabilityRequestBuilder withSection(String airline, String departure, String arrival) {
        Section section = new SectionBuilder()
                .withAirline(airline)
                .withDeparture(departure)
                .withArrival(arrival)
                .build();

        return withSection(section);
    }

    public AvailabilityRequestBuilder withPassengers(Passengers passengers) {
        this.passengers = passengers != null ? passengers : Passengers.EMPTY_PASSENGERS;
        return this;
    }

    public AvailabilityRequest build() {
        AvailabilityRequest result = new AvailabilityRequest(sectionList, passengers);
        Checker.checkValidityWithJSR380(result);
        return result;
    }
}
