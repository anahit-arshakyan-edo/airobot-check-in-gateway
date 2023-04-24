package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.AvailabilityRequestBuilder;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.SectionBuilder;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;

import java.util.List;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Passengers.EMPTY_PASSENGERS;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class AvailabilityRequestMapper {

    AvailabilityRequest map(CheckInAvailabilityRequest requestDto) {
        return new AvailabilityRequestBuilder()
                .withSections(map(requestDto.getSections()))
                .withPassengers(map(requestDto.getPassengers()))
                .build();
    }

    private List<Section> map(List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section> sectionDtos) {
        if (sectionDtos == null) {
            return emptyList();
        }

        return sectionDtos.stream()
                .map(this::map)
                .collect(toList());
    }

    private Section map(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section sectionDto) {
        return new SectionBuilder()
                .withAirline(sectionDto.getOperatingCarrier())
                .withDeparture(sectionDto.getDepartureAirport())
                .withArrival(sectionDto.getArrivalAirport())
                .build();
    }

    private Passengers map(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Passengers passengersDto) {
        if (passengersDto == null) {
            return EMPTY_PASSENGERS;
        }
        return new Passengers(
                passengersDto.getCitizenships(),
                passengersDto.getAdults(),
                passengersDto.getChildren(),
                passengersDto.getInfants());
    }
}
