package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.BoardingPassDeliveryCustomization;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.SectionId;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.CheckInData;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.PassengerJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.ScheduledJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse;

import java.util.ArrayList;
import java.util.List;

public class SegmentCheckInMapper {

    public SegmentCheckIn from(CreateCheckInResponse createCheckInResponse) {

        CheckInData checkInData = createCheckInResponse.getData();
        List<BoardingPass> requestedBoardingPasses = new ArrayList<>();

        for (com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger : checkInData.getPassengers()) {
            for (com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.ScheduledJourney journey : checkInData.getJourneys()) {
                PassengerJourney passengerJourneyToMap = matchPassengerWithJourney(checkInData.getPassengerJourneys(), passenger, journey);
                requestedBoardingPasses.add(getBoardingPass(passenger, journey, passengerJourneyToMap));
            }
        }

        return new SegmentCheckIn(
                SegmentCheckInId.create(),
                ProviderRequestId.from(checkInData.getRequestId()),
                new BoardingPassDeliveryCustomization(
                        checkInData.getBrand(),
                        checkInData.getLang(),
                        checkInData.getCountry(),
                        checkInData.getBookingEmail(),
                        checkInData.getEmail())
                ,
                requestedBoardingPasses
        );
    }

    private PassengerJourney matchPassengerWithJourney(List<PassengerJourney> passengerJourneys, com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger, ScheduledJourney journey) {
        return passengerJourneys.stream()
                .filter((passengerJourney) -> passenger.getId() == passengerJourney.getPassengerId()
                        && journey.getId() == passengerJourney.getJourneyId())
                .findAny()
                .orElseThrow();
    }

    private BoardingPass getBoardingPass(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger, ScheduledJourney scheduledJourney, com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.PassengerJourney passengerJourney) {
        return new BoardingPass(
                BoardingPassId.create(),
                getSection(scheduledJourney),
                getPassenger(passenger),
                new Status(
                        Status.Code.valueOf(passengerJourney.getStatus().toUpperCase()),
                        null
                ),
                ProviderPassengerSectionId.from(passengerJourney.getPassengerJourneyId())
        );
    }

    private Section getSection(ScheduledJourney journey) {
        return new Section(
                SectionId.create(),
                ProviderSectionId.from(journey.getId()),
                Airline.create(journey.getAirline()),
                Airline.create(journey.getAirline()),
                Airport.create(journey.getDepartureAirport()),
                Airport.create(journey.getArrivalAirport()),
                journey.getDepartureDate(),
                journey.getArrivalDate(),
                journey.getFlightNumber(),
                journey.getPnr()
        );
    }

    private Passenger getPassenger(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger) {
        return new Passenger(
                PassengerId.create(),
                ProviderPassengerId.from(passenger.getId()),
                passenger.getName(),
                passenger.getLastName(),
                passenger.getDateOfBirth(),
                getPassengerGender(passenger.getGender()),
                passenger.getNationality(),
                getDocument(passenger.getDocument())
        );
    }

    private Gender getPassengerGender(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Gender gender) {
        switch (gender) {
            case MALE:
                return Gender.M;
            case FEMALE:
                return Gender.F;
            default:
                return null;
        }
    }

    private Document getDocument(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document document) {
        return new Document(
                DocumentType.valueOf(document.getType().name()),
                document.getNumber(),
                document.getExpireDate(),
                document.getIssueDate(),
                document.getCountry()
        );
    }

}
