package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
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

    public CreateCheckInRequest map(ProviderReferenceId providerReferenceId, ProviderRequest providerRequest) {

        List<BoardingPass> boardingPasses = providerRequest.boardingPasses();

        List<Passenger> passengers = new ArrayList<>();
        List<ScheduledJourney> journeys = new ArrayList<>();
        DeliveryOptions deliveryOptions = providerRequest.deliveryOptions();

        for (BoardingPass boardingPass: boardingPasses) {
            passengers.add(mapPassenger(boardingPass.passenger()));
            journeys.add(mapJourney(boardingPass.section(), providerReferenceId.valueLong()));
        }

        return CreateCheckInRequest.builder()
                .bookingEmail(deliveryOptions.bookingEmail())
                .email(deliveryOptions.deliveryEmail())
                .lang(deliveryOptions.language())
                .brand(deliveryOptions.brand())
                .country(deliveryOptions.country())
                .passengers(passengers)
                .journeys(journeys)
                .build();
    }

    private Passenger mapPassenger(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger passengerToMap) {

        Passenger passenger = new Passenger();
        passenger.setName(passengerToMap.name());
        passenger.setLastName(passengerToMap.lastName());
        passenger.setNationality(passengerToMap.nationality());
        passenger.setGender(mapGender(passengerToMap.gender()));
        passenger.setDateOfBirth(passengerToMap.dateOfBirth());
        passenger.setDocument(mapDocument(passengerToMap.document()));

        return passenger;
    }

    private Gender mapGender(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender gender) {
        if (gender == null) {
            return null;
        }

        switch (gender) {
        case M :
            return Gender.MALE;
        case F :
            return Gender.FEMALE;
        default: throw new IllegalStateException("Unexpected gender: " + gender);
        }
    }

    private Document mapDocument(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document documentToMap) {
        if (documentToMap == null) {
            return null;
        }
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
