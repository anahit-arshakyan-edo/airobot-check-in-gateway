package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInTestObjectMother;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.SectionId;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

public class BoardingPassBuilderTest {

    @Test
    public void boardingPass() {

        BoardingPass actualBoardingPass = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(createSection())
                .withPassenger(createPassenger())
                .withStatus(new Status(Status.Code.SUCCEED, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.from(110))
                .build();

        BoardingPass expectedBoardingPass = ItineraryCheckInTestObjectMother.outboundBoardingPasses().get(0);
        assertEquals(actualBoardingPass, expectedBoardingPass);
    }

    private Passenger createPassenger() {
        return Passenger.builder()
                .withId(PassengerId.create())
                .withProviderPassengerId(ProviderPassengerId.from(1))
                .withName("Jon")
                .withLastName("Doe")
                .withDateOfBirth(LocalDate.of(1980, 2, 1))
                .withGender(Gender.M)
                .withNationality("ES")
                .withDocument(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document.create(
                        DocumentType.PASSPORT, "ABC123", LocalDate.of(2025, 10, 1), LocalDate.of(2020, 10, 1), "ES"
                ))
                .build();
    }

    private Section createSection() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.from(1))
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("MXP"))
                .withArrivalAirport(Airport.create("BCN"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 1, 22, 00, 30))
                .withArrivalDate(LocalDateTime.of(2023, 2, 1, 23, 00, 40))
                .withFlightNumber(1234)
                .withPnr("ABC123")
                .build();
    }

}
