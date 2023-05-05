package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.BoardingPassDeliveryCustomization;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.DocumentType;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Gender;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.ScheduledJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.request.CreateCheckInRequest;
import java.util.ArrayList;
import java.util.List;

public class CreateCheckInRequestMapper {

    public CreateCheckInRequest map(SegmentCheckIn segmentCheckIn, long referenceId) {

        List<Passenger> passengers = new ArrayList<>();
        List<ScheduledJourney> journeys = new ArrayList<>();
        BoardingPassDeliveryCustomization boardingPassDeliveryCustomization = segmentCheckIn.boardingPassDeliveryCustomization();

        for (BoardingPass boardingPass: segmentCheckIn.boardingPasses()) {
            passengers.add(mapPassenger(boardingPass.passenger()));
            journeys.add(mapJourney(boardingPass.section(), referenceId));
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

    private Passenger mapPassenger(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger passengerToMap) {

        Passenger passenger = new Passenger();
        passenger.setName(passengerToMap.name());
        passenger.setLastName(passengerToMap.lastName());
        passenger.setNationality(passengerToMap.nationality());
        passenger.setGender(Gender.valueOf(passengerToMap.gender().description()));
        passenger.setDateOfBirth(passengerToMap.dateOfBirth());
        passenger.setDocument(mapDocument(passengerToMap.document()));

        return passenger;
    }

    private Document mapDocument(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document documentToMap) {
        Document document = new Document();
        document.setType(DocumentType.valueOf(documentToMap.type().name()));
        document.setNumber(documentToMap.number());
        document.setCountry(documentToMap.country());
        document.setExpireDate(documentToMap.expireDate());
        document.setIssueDate(documentToMap.issueDate());
        return document;
    }

    private ScheduledJourney mapJourney(Section section, long referenceId) {
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
