package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AvailabilityRequestMapper {

    AvailabilityRequestMapper INSTANCE = Mappers.getMapper(AvailabilityRequestMapper.class);

    AvailabilityRequest map(CheckInAvailabilityRequest checkInAvailabilityRequest);

    Passengers map(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Passengers passengers);

    @Mapping(source = "operatingCarrier", target = "airline.iataCode")
    @Mapping(source = "arrivalAirport", target = "arrival.iataCode")
    @Mapping(source = "departureAirport", target = "departure.iataCode")
    Section map(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section section);
}
