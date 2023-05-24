package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.PassengerJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.ScheduledJourney;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateCheckInTestObjectMother {

    public static final com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse
            CREATE_CHECK_IN_RESPONSE_DTO = createCheckInResponse();

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse createCheckInResponse() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse createCheckInResponse =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse();
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.CheckInData checkInData =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.CheckInData();
        checkInData.setJourneys(airobotScheduledJourneys());
        checkInData.setPassengers(Collections.singletonList(airobotPassenger()));
        checkInData.setBrand("EDREAMS");
        checkInData.setBookingEmail("jon@doe.com");
        checkInData.setEmail("jon@doe.com");
        checkInData.setCountry("ES");
        checkInData.setLang("es_ES");
        checkInData.setRequestId("12345ABCDEF");
        checkInData.setPassengerJourneys(airobotPassengerJourneys());
        createCheckInResponse.setData(checkInData);
        return createCheckInResponse;
    }

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger airobotPassenger() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger = new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger();
        passenger.setId(1);
        passenger.setName("Jon");
        passenger.setLastName("Doe");
        passenger.setNationality("ES");
        passenger.setGender(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Gender.MALE);
        passenger.setDateOfBirth(LocalDate.of(1980, 2, 1));
        passenger.setDocument(airobotDocument());
        return passenger;
    }

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document airobotDocument() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document document = new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document();
        document.setType(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.DocumentType.PASSPORT);
        document.setNumber("123456");
        document.setCountry("ES");
        document.setIssueDate(LocalDate.of(2020, 10, 1));
        document.setExpireDate(LocalDate.of(2025, 10, 1));
        return document;
    }

    private static List<PassengerJourney> airobotPassengerJourneys() {
        List<PassengerJourney> passengerJourneys = new ArrayList<>();

        PassengerJourney outboundPassengerJourney = new PassengerJourney();
        outboundPassengerJourney.setPassengerId(1);
        outboundPassengerJourney.setJourneyId(1);
        outboundPassengerJourney.setPassengerJourneyId(1);
        outboundPassengerJourney.setStatus("pending");

        PassengerJourney inboundPassengerJourney = new PassengerJourney();
        inboundPassengerJourney.setPassengerId(1);
        inboundPassengerJourney.setJourneyId(2);
        inboundPassengerJourney.setPassengerJourneyId(2);
        inboundPassengerJourney.setStatus("pending");

        passengerJourneys.add(outboundPassengerJourney);
        passengerJourneys.add(inboundPassengerJourney);
        return passengerJourneys;
    }

    private static List<ScheduledJourney> airobotScheduledJourneys() {
        List<ScheduledJourney> scheduledJourneys = new ArrayList<>();

        ScheduledJourney outboundJourney = new ScheduledJourney();
        outboundJourney.setId(1);
        outboundJourney.setAirline("FR");
        outboundJourney.setDepartureAirport("MXP");
        outboundJourney.setArrivalAirport("BCN");
        outboundJourney.setDepartureDate(LocalDateTime.of(2023, 2, 1, 22, 0));
        outboundJourney.setArrivalDate(LocalDateTime.of(2023, 2, 1, 23, 0));
        outboundJourney.setPnr("ABC123");
        outboundJourney.setFlightNumber(1234);
        outboundJourney.setBookingId(123456789);

        ScheduledJourney inboundJourney = new ScheduledJourney();
        inboundJourney.setId(2);
        inboundJourney.setAirline("FR");
        inboundJourney.setDepartureAirport("BCN");
        inboundJourney.setArrivalAirport("MXP");
        inboundJourney.setDepartureDate(LocalDateTime.of(2023, 2, 5, 22, 0));
        inboundJourney.setArrivalDate(LocalDateTime.of(2023, 2, 5, 23, 0));
        inboundJourney.setPnr("ABC123");
        inboundJourney.setFlightNumber(1234);
        inboundJourney.setBookingId(123456789);

        scheduledJourneys.add(outboundJourney);
        scheduledJourneys.add(inboundJourney);
        return scheduledJourneys;
    }

}
