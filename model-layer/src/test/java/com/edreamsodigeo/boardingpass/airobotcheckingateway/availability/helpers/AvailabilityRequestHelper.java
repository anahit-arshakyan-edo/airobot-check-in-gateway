package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Section;

import java.util.List;

public class AvailabilityRequestHelper {
    public static Section sectionOf(String airline, String departure, String arrival) {
        return new Section(new Airline(airline), new Airport(departure), new Airport(arrival));
    }

    public static AvailabilityRequest requestOf(Section section) {
        return requestOf(section != null ? List.of(section) : null, new Passengers());
    }

    public static  AvailabilityRequest requestOf(List<Section> sections) {
        return requestOf(sections, new Passengers());
    }

    public static  AvailabilityRequest requestOf(Section section, Passengers passengers) {
        return requestOf(List.of(section), passengers);
    }

    public static  AvailabilityRequest requestOf(List<Section> sections, Passengers passengers) {
        return new AvailabilityRequest(sections, passengers);
    }
}
