package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.validation.Checker;

public class SectionBuilder {
    private String airline;
    private String departure;
    private String arrival;

    public SectionBuilder withAirline(String airline) {
        this.airline = airline;
        return this;
    }

    public SectionBuilder withDeparture(String departure) {
        this.departure = departure;
        return this;
    }

    public SectionBuilder withArrival(String arrival) {
        this.arrival = arrival;
        return this;
    }

    public Section build() {
        Section result = new Section(Airline.create(airline), Airport.create(departure), Airport.create(arrival));
        Checker.checkValidityWithJSR380(result);
        return result;
    }
}
