package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.BoardingPassDeliveryCustomization;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.SectionId;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Segment;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ItineraryCheckInMapper {

    public ItineraryCheckIn from(CreateCheckInRequest createCheckInRequestDto) {

        List<SegmentCheckIn> segmentCheckIns = new ArrayList<>();
        List<Segment> flightSegments = createCheckInRequestDto.getItinerary().getSegments();
        List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger> passengers = createCheckInRequestDto.getPassengers();

        for (com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger passenger : passengers) {
            for (Segment segment : flightSegments) {
                segmentCheckIns.add(getSegmentCheckIn(passenger, segment, getBoardingPassDeliveryCustomization(createCheckInRequestDto)));
            }
        }

        return new ItineraryCheckIn(ItineraryCheckInId.create(), segmentCheckIns);
    }

    private SegmentCheckIn getSegmentCheckIn(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger passenger, Segment flightSegment, BoardingPassDeliveryCustomization boardingPassDeliveryCustomization) {

        List<BoardingPass> boardingPasses = new ArrayList<>();

        for (com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Section section : flightSegment.getSections()) {
            boardingPasses.add(getBoardingPass(getSection(section), getPassenger(passenger), new Status(Status.Code.INITIALIZED, null)));
        }

        return new SegmentCheckIn(
                SegmentCheckInId.create(),
                ProviderRequestId.notAssigned(),
                boardingPassDeliveryCustomization,
                boardingPasses
        );
    }

    private BoardingPassDeliveryCustomization getBoardingPassDeliveryCustomization(CreateCheckInRequest createCheckInRequestDto) {
        return new BoardingPassDeliveryCustomization(
                createCheckInRequestDto.getWebsite().getBrand().name(),
                createCheckInRequestDto.getLocale(),
                createCheckInRequestDto.getWebsite().getCode(),
                createCheckInRequestDto.getBuyer().getEmail(),
                createCheckInRequestDto.getBuyer().getEmail()
        );
    }

    private BoardingPass getBoardingPass(Section section, Passenger passenger, Status status) {
        return new BoardingPass(
                BoardingPassId.create(),
                section,
                passenger,
                status,
                ProviderPassengerSectionId.notAssigned());
    }

    private Section getSection(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Section section) {
        return new Section(
                SectionId.create(),
                ProviderSectionId.notAssigned(),
                Airline.create(section.getMarketingCarrier()),
                Airline.create(section.getOperatingCarrier()),
                Airport.create(section.getDepartureAirport()),
                Airport.create(section.getArrivalAirport()),
                section.getDepartureDate(),
                section.getArrivalDate(),
                Integer.parseInt(section.getFlightNumber()),
                section.getPnr());
    }

    private Passenger getPassenger(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger passenger) {
        return new Passenger(
                PassengerId.create(),
                ProviderPassengerId.notAssigned(),
                StringUtils.isNotBlank(passenger.getMiddleName()) ? passenger.getName() + " " + passenger.getMiddleName() : passenger.getName(),
                passenger.getLastName(),
                passenger.getDateOfBirth(),
                Gender.valueOf(passenger.getGender()),
                passenger.getNationality(),
                getDocument(passenger.getDocument())
        );
    }

    private Document getDocument(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Document document) {
        return new Document(
                DocumentType.valueOf(document.getType().name()),
                document.getNumber(),
                document.getExpireDate(),
                document.getIssueDate(),
                document.getCountry()
        );
    }

}
