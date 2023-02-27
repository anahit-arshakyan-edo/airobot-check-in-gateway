package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.model.Journey;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.request.AvailabilityRequest;

import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityRequestMapper {
    public AvailabilityRequest map(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest request) {
        List<Journey> journeysDto = mapFromSectionList(request.getSections());
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.model.Passengers passengersDto = mapFromPassengers(request.getPassengers());
        return mapFromAvailabilityRequest(journeysDto, passengersDto);
    }

    private AvailabilityRequest mapFromAvailabilityRequest(
            List<Journey> journeysDto, com.edreamsodigeo.boardingpass.airobotproviderapi.v1.model.Passengers passengersDto) {

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

    private com.edreamsodigeo.boardingpass.airobotproviderapi.v1.model.Passengers mapFromPassengers(Passengers passengers) {
        return com.edreamsodigeo.boardingpass.airobotproviderapi.v1.model.Passengers.builder()
                .citizenship(passengers.getCitizenships().get(0)) //Super contract receive a list, but aiorbot we only have a string, it's correct?
                .adults(passengers.getAdults())
                .teens(0) // airobot model has this field that is not present in super-contract
                .children(passengers.getChildren())
                .infants(passengers.getInfants())
                .build();
    }

    private Journey mapFromSection(Section s) {
        return Journey.builder()
                .airline(s.getAirlineIataCode())
                .arrivalAirport(s.getArrivalAirportIataCode())
                .departureAirport(s.getDepartureAirportIataCode())
                .build();
    }

}
