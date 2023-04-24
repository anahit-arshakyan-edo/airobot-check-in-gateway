package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.BoardingPassDeliveryCustomization;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.DocumentType;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Gender;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.ScheduledJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.request.CreateCheckInRequest;
import java.util.ArrayList;
import java.util.List;

public class CreateCheckInRequestMapper {

    public CreateCheckInRequest from(SegmentCheckIn segmentCheckIn, long referenceId) {

        List<Passenger> passengers = new ArrayList<>();
        List<ScheduledJourney> journeys = new ArrayList<>();
        BoardingPassDeliveryCustomization boardingPassDeliveryCustomization = segmentCheckIn.boardingPassDeliveryCustomization();

        for (BoardingPass boardingPass: segmentCheckIn.boardingPasses()) {
            passengers.add(getPassenger(boardingPass.passenger()));
            journeys.add(getJourney(boardingPass.section(), referenceId));
        }

        return CreateCheckInRequest.builder()
                .bookingEmail(boardingPassDeliveryCustomization.bookingEmail())
                .email(boardingPassDeliveryCustomization.deliveryEmail())
                .lang(boardingPassDeliveryCustomization.language())
                .brand(boardingPassDeliveryCustomization.brand())
                .country(boardingPassDeliveryCustomization.country())
                .passengers(passengers)
                .journeys(journeys)
                .build();
    }

    private Passenger getPassenger(com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Passenger passengerToMap) {

        Passenger passenger = new Passenger();
        passenger.setName(passengerToMap.name());
        passenger.setLastName(passengerToMap.lastName());
        passenger.setNationality(passengerToMap.nationality());
        passenger.setGender(Gender.valueOf(passengerToMap.gender().description()));
        passenger.setDateOfBirth(passengerToMap.dateOfBirth());
        passenger.setDocument(getDocument(passengerToMap.document()));

        return passenger;
    }

    private Document getDocument(com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Document documentToMap) {
        Document document = new Document();
        document.setType(DocumentType.valueOf(documentToMap.type().name()));
        document.setNumber(documentToMap.number());
        document.setCountry(documentToMap.country());
        document.setExpireDate(documentToMap.expireDate());
        document.setIssueDate(documentToMap.issueDate());
        return document;
    }

    private ScheduledJourney getJourney(Section section, long referenceId) {
        ScheduledJourney journey = new ScheduledJourney();

        journey.setAirline(section.operatingCarrier().iataCode());
        journey.setDepartureAirport(section.departureAirport().iataCode());
        journey.setArrivalAirport(section.arrivalAirport().iataCode());
        journey.setDepartureDate(section.departureDate());
        journey.setArrivalDate(section.arrivalDate());
        journey.setFlightNumber(section.flightNumber());
        journey.setPnr(section.pnr());
        journey.setBookingId(referenceId);

        return journey;
    }
}
