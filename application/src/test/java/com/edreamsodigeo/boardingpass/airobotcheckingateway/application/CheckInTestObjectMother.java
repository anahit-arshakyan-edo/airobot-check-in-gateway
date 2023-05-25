package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
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

public class CheckInTestObjectMother {

    public static final String OUTBOUND_PROVIDER_REQUEST_ID = "cd80080c-ebe7-4aa6-9f33-da33f7061f74";
    public static final String INBOUND_PROVIDER_REQUEST_ID = "ab20040f-aab9-4ju0-7u98-tr54t9668p72";

    public static ItineraryCheckIn oneWayWithoutStopsItineraryCheckIn() {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        SegmentCheckIn outboundSegment = SegmentCheckIn.from(outboundBoardingPassesWithoutStops(), boardingPassDeliveryCustomization());
        segmentCheckIns.add(outboundSegment);
        return ItineraryCheckIn.from(ItineraryCheckInId.create(), segmentCheckIns);
    }

    public static ItineraryCheckIn oneWayWithStopsItineraryCheckIn() {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        SegmentCheckIn outboundSegment = SegmentCheckIn.from(outboundBoardingPassesWithStops(), boardingPassDeliveryCustomization());
        segmentCheckIns.add(outboundSegment);
        return ItineraryCheckIn.from(ItineraryCheckInId.create(), segmentCheckIns);
    }

    public static ItineraryCheckIn roundTripWithoutStopsItineraryCheckIn() {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        SegmentCheckIn outboundSegment = SegmentCheckIn.from(outboundBoardingPassesWithoutStops(), boardingPassDeliveryCustomization());
        SegmentCheckIn inboundSegment = SegmentCheckIn.from(inboundBoardingPassesWithoutStops(), boardingPassDeliveryCustomization());
        segmentCheckIns.add(outboundSegment);
        segmentCheckIns.add(inboundSegment);
        return ItineraryCheckIn.from(ItineraryCheckInId.create(), segmentCheckIns);
    }

    public static ItineraryCheckIn roundTripWithStopsItineraryCheckIn() {
        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        SegmentCheckIn outboundSegment = SegmentCheckIn.from(outboundBoardingPassesWithStops(), boardingPassDeliveryCustomization());
        SegmentCheckIn inboundSegment = SegmentCheckIn.from(inboundBoardingPassesWithStops(), boardingPassDeliveryCustomization());
        segmentCheckIns.add(outboundSegment);
        segmentCheckIns.add(inboundSegment);
        return ItineraryCheckIn.from(ItineraryCheckInId.create(), segmentCheckIns);
    }

    private static DeliveryOptions boardingPassDeliveryCustomization() {
        return DeliveryOptions.builder()
                .withBrand("EDREAMS")
                .withLanguage("es_ES")
                .withCountry("ES")
                .withBookingEmail("jon@doe.com")
                .withDeliveryEmail("jon@doe.com")
                .build();
    }

    private static List<BoardingPass> outboundBoardingPassesWithoutStops() {
        List<BoardingPass> boardingPasses = new ArrayList<>();

        BoardingPass boardingPass = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(outboundSectionA())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        boardingPasses.add(boardingPass);
        return boardingPasses;
    }

    private static List<BoardingPass> outboundBoardingPassesWithStops() {
        List<BoardingPass> boardingPasses = new ArrayList<>();

        BoardingPass boardingPassA = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(outboundSectionA())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        BoardingPass boardingPassB = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(outboundSectionB())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        boardingPasses.add(boardingPassA);
        boardingPasses.add(boardingPassB);

        return boardingPasses;
    }

    private static List<BoardingPass> inboundBoardingPassesWithoutStops() {
        List<BoardingPass> boardingPasses = new ArrayList<>();

        BoardingPass boardingPass = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(inboundSectionA())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        boardingPasses.add(boardingPass);
        return boardingPasses;
    }

    private static List<BoardingPass> inboundBoardingPassesWithStops() {
        List<BoardingPass> boardingPasses = new ArrayList<>();

        BoardingPass boardingPassA = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(inboundSectionA())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        BoardingPass boardingPassB = BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(inboundSectionB())
                .withPassenger(passenger())
                .withStatus(new Status(Status.Code.PENDING, null))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();

        boardingPasses.add(boardingPassA);
        boardingPasses.add(boardingPassB);

        return boardingPasses;
    }

    private static Passenger passenger() {
        return Passenger.builder()
                .withId(PassengerId.create())
                .withProviderPassengerId(ProviderPassengerId.notAssigned())
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

    private static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section outboundSectionA() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("MXP"))
                .withArrivalAirport(Airport.create("BCN"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 1, 22, 0))
                .withArrivalDate(LocalDateTime.of(2023, 2, 1, 23, 0))
                .withFlightNumber(1234)
                .withPnr("ABC123")
                .build();
    }

    private static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section outboundSectionB() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("BCN"))
                .withArrivalAirport(Airport.create("JFK"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 1, 22, 0))
                .withArrivalDate(LocalDateTime.of(2023, 2, 1, 23, 0))
                .withFlightNumber(1234)
                .withPnr("ABC456")
                .build();
    }

    private static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section inboundSectionA() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("JFK"))
                .withArrivalAirport(Airport.create("BCN"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 5, 22, 0))
                .withArrivalDate(LocalDateTime.of(2023, 2, 5, 23, 0))
                .withFlightNumber(1234)
                .withPnr("ABC123")
                .build();
    }

    private static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section inboundSectionB() {
        return com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create("FR"))
                .withDepartureAirport(Airport.create("BCN"))
                .withArrivalAirport(Airport.create("MXP"))
                .withDepartureDate(LocalDateTime.of(2023, 2, 5, 22, 0))
                .withArrivalDate(LocalDateTime.of(2023, 2, 5, 23, 0))
                .withFlightNumber(1234)
                .withPnr("ABC456")
                .build();
    }

}
