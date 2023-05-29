package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.SectionId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItineraryCheckInTestObjectMother {

    public static final ItineraryCheckIn itineraryCheckIn() {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        SegmentCheckIn outboundSegment = SegmentCheckIn.from(outboundBoardingPasses(), boardingPassDeliveryCustomization());
        SegmentCheckIn inboundSegment = SegmentCheckIn.from(inboundBoardingPasses(), boardingPassDeliveryCustomization());
        segmentCheckIns.add(outboundSegment);
        segmentCheckIns.add(inboundSegment);
        return ItineraryCheckIn.from(ItineraryCheckInId.create(), ProviderReferenceId.from(1L), segmentCheckIns);
    }

    public static Passenger passenger() {
        return Passenger.builder()
                .withId(PassengerId.create())
                .withProviderPassengerId(ProviderPassengerId.notAssigned())
                .withName("Jon")
                .withLastName("Doe")
                .withDateOfBirth(LocalDate.of(1980, 2, 1))
                .withGender(Gender.M)
                .withNationality("ES")
                .withDocument(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document.create(
                        DocumentType.PASSPORT, "ABC123", LocalDate.of(2025, 10, 1), null, "ES"
                ))
                .build();
    }

    public static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section outboundSection() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("MXP"))
                .withArrivalAirport(Airport.create("BCN"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 1, 22, 0, 10))
                .withArrivalDate(LocalDateTime.of(2023, 2, 1, 23, 0, 20))
                .withFlightNumber(1234)
                .withPnr("ABC123")
                .build();
    }

    public static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section inboundSection() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("BCN"))
                .withArrivalAirport(Airport.create("MXP"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 5, 22, 0))
                .withArrivalDate(LocalDateTime.of(2023, 2, 5, 23, 0))
                .withFlightNumber(1234)
                .withPnr("ABC123")
                .build();
    }

    public static List<BoardingPass> outboundBoardingPasses() {
        List<BoardingPass> boardingPasses = new ArrayList<>();

        BoardingPass boardingPass = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(outboundSection())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        boardingPasses.add(boardingPass);
        return boardingPasses;
    }

    public static List<BoardingPass> inboundBoardingPasses() {
        List<BoardingPass> boardingPasses = new ArrayList<>();

        BoardingPass boardingPass = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(inboundSection())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        boardingPasses.add(boardingPass);
        return boardingPasses;
    }

    public static DeliveryOptions boardingPassDeliveryCustomization() {
        return DeliveryOptions.builder()
                .withBrand("EDREAMS")
                .withLanguage("es_ES")
                .withCountry("ES")
                .withBookingEmail("jon@doe.com")
                .withDeliveryEmail("jon@doe.com")
                .build();
    }


}
