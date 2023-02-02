package com.edreamsodigeo.boardingpass.airobotcheckingateway;

public class AvailabilityRequest {

    private Section section;

    public AvailabilityRequest(Section section) {
        this.section = section;
    }

    public boolean isValid() {
        if (! isAirportPresent(section.arrival()))
            return false;

        if (! isAirportPresent(section.departure()))
            return false;

        return true;
    }

    private boolean isAirportPresent(String airport) {
        return airport != null && ! airport.isEmpty();
    }

}
