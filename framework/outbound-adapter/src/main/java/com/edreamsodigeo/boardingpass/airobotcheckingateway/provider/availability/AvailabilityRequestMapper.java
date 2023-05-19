package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Journey;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.request.AvailabilityRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityRequestMapper {
    public AvailabilityRequest map(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest request) {
        List<Journey> journeysDto = mapFromSectionList(request.sections());
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passengers passengersDto = mapFromPassengers(request.passengers());
        return mapFromAvailabilityRequest(journeysDto, passengersDto);
    }

    private AvailabilityRequest mapFromAvailabilityRequest(
            List<Journey> journeysDto, com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passengers passengersDto) {

        return AvailabilityRequest.builder()
                .journeys(journeysDto)
                .passengers(passengersDto)
                .build();
    }

    private List<Journey> mapFromSectionList(List<Section> sections) {
        return sections.stream()
                .map(this::mapFromSection)
                .collect(Collectors.toList());
    }

    private com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passengers mapFromPassengers(Passengers passengers) {
        return com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passengers.builder()
                .citizenship((passengers.citizenships() == null || passengers.citizenships().isEmpty()) ? null : passengers.citizenships().get(0)) //Super contract receive a list, but aiorbot we only have a string, it's correct?
                .adults(passengers.adults())
                .teens(0) // airobot model has this field that is not present in super-contract
                .children(passengers.children())
                .infants(passengers.infants())
                .build();
    }

    private Journey mapFromSection(Section s) {
        return Journey.builder()
                .airline(s.airlineIataCode())
                .arrivalAirport(s.arrivalAirportIataCode())
                .departureAirport(s.departureAirportIataCode())
                .build();
    }

}
