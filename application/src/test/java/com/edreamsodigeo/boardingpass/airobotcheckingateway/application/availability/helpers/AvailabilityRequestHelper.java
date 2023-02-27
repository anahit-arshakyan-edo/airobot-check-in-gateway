package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.helpers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;

import java.util.List;

public class AvailabilityRequestHelper {
    public static Section sectionOf(String airline, String departure, String arrival) {
        return new Section(new Airline(airline), new Airport(departure), new Airport(arrival));
    }

    public static AvailabilityRequest requestOf(Section section) {
        return requestOf(section != null ? List.of(section) : null, Passengers.EMPTY_PASSENGERS);
    }

    public static  AvailabilityRequest requestOf(List<Section> sections) {
        return requestOf(sections, Passengers.EMPTY_PASSENGERS);
    }

    public static  AvailabilityRequest requestOf(List<Section> sections, Passengers passengers) {
        return new AvailabilityRequest(sections, passengers);
    }
}
