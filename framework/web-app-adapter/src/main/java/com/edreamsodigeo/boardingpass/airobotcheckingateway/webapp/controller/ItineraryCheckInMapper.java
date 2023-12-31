package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.SectionId;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Segment;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItineraryCheckInMapper {

    public ItineraryCheckIn map(CreateCheckInRequest createCheckInRequestDto) {

        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        List<Segment> flightSegments = createCheckInRequestDto.getItinerary().getSegments();
        List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger> passengers = createCheckInRequestDto.getPassengers();

        for (Segment segment : flightSegments) {
            segmentCheckIns.add(mapSegmentCheckIn(passengers, segment, mapBoardingPassDeliveryCustomization(createCheckInRequestDto)));
        }

        return ItineraryCheckIn.from(ItineraryCheckInId.create(), ProviderReferenceId.notAssigned(), segmentCheckIns);
    }

    private SegmentCheckIn mapSegmentCheckIn(List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger> passengers, Segment flightSegment, DeliveryOptions deliveryOptions) {

        List<BoardingPass> boardingPasses = new ArrayList<>();

        for (com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Section section : flightSegment.getSections()) {
            for (com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger passenger : passengers) {
                boardingPasses.add(mapBoardingPass(mapSection(section), mapPassenger(passenger), new Status(Status.Code.INITIALIZED, null)));
            }
        }

        return SegmentCheckIn.from(
                boardingPasses,
                deliveryOptions
        );
    }

    private DeliveryOptions mapBoardingPassDeliveryCustomization(CreateCheckInRequest createCheckInRequestDto) {
        return DeliveryOptions.builder()
                .withBrand(createCheckInRequestDto.getWebsite().getBrand().name())
                .withLanguage(createCheckInRequestDto.getLocale())
                .withCountry(createCheckInRequestDto.getWebsite().getCode())
                .withBookingEmail(createCheckInRequestDto.getItinerary().getBookingEmail())
                .withDeliveryEmail(createCheckInRequestDto.getBuyer().getEmail())
                .build();
    }

    private BoardingPass mapBoardingPass(Section section, Passenger passenger, Status status) {

        return BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(section)
                .withPassenger(passenger)
                .withStatus(status)
                .withProviderPassengerSectionId(ProviderPassengerSectionId.notAssigned())
                .build();
    }

    private Section mapSection(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Section section) {
        return Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.notAssigned())
                .withOperatingCarrier(Airline.create(section.getOperatingCarrier()))
                .withDepartureAirport(Airport.create(section.getDepartureAirport()))
                .withArrivalAirport(Airport.create(section.getArrivalAirport()))
                .withDepartureDate(section.getDepartureDate())
                .withArrivalDate(section.getArrivalDate())
                .withFlightNumber(Integer.parseInt(section.getFlightNumber()))
                .withPnr(section.getPnr())
                .build();
    }

    private Passenger mapPassenger(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger passenger) {
        return Passenger.builder()
                .withId(PassengerId.create())
                .withProviderPassengerId(ProviderPassengerId.notAssigned())
                .withName(StringUtils.isNotBlank(passenger.getMiddleName()) ? passenger.getName() + " " + passenger.getMiddleName() : passenger.getName())
                .withLastName(passenger.getLastName())
                .withDateOfBirth(passenger.getDateOfBirth())
                .withGender(mapGender(passenger))
                .withNationality(passenger.getNationality())
                .withDocument(mapDocument(passenger.getDocument()))
                .build();
    }

    private Gender mapGender(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger passenger) {
        if (passenger.getGender() == null) {
            return null;
        }
        switch (passenger.getGender().toUpperCase(Locale.ENGLISH)) {
        case "MALE" :
        case "M" :
            return Gender.M;
        case "FEMALE" :
        case "F" :
            return Gender.F;
        default: throw new IllegalStateException("Unexpected gender: " + passenger.getGender());
        }
    }

    private Document mapDocument(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Document document) {
        if (document == null) {
            return null;
        }
        return Document.create(
                DocumentType.valueOf(document.getType().name()),
                document.getNumber(),
                document.getExpireDate(),
                document.getIssueDate(),
                document.getCountry()
        );
    }

}
