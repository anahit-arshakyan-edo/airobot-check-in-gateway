package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.SectionId;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.CheckInData;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.PassengerJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.ScheduledJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.response.CheckInStatusResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("CPD-START")
public class SegmentCheckInMapper {

    public SegmentCheckIn map(CheckInStatusResponse checkInStatusResponse) {

        CheckInData checkInData = checkInStatusResponse.getData();
        List<BoardingPass> requestedBoardingPasses = new ArrayList<>();

        for (com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.Passenger passenger : checkInData.getPassengers()) {
            for (ScheduledJourney journey : checkInData.getJourneys()) {
                PassengerJourney passengerJourneyToMap = mapPassengerJourney(checkInData.getPassengerJourneys(), passenger, journey);
                requestedBoardingPasses.add(mapBoardingPass(passenger, journey, passengerJourneyToMap));
            }
        }

        return SegmentCheckIn.from(requestedBoardingPasses,
                DeliveryOptions.builder()
                        .withBrand(checkInData.getBrand())
                        .withLanguage(checkInData.getLang())
                        .withCountry(checkInData.getCountry())
                        .withBookingEmail(checkInData.getBookingEmail())
                        .withDeliveryEmail(checkInData.getEmail())
                        .build());
    }

    private PassengerJourney mapPassengerJourney(List<PassengerJourney> passengerJourneys, com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.Passenger passenger, ScheduledJourney journey) {
        return passengerJourneys.stream()
                .filter((passengerJourney) -> passenger.getId() == passengerJourney.getPassengerId()
                        && journey.getId() == passengerJourney.getJourneyId())
                .findAny()
                .orElseThrow();
    }

    private BoardingPass mapBoardingPass(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.Passenger passenger, ScheduledJourney scheduledJourney, PassengerJourney passengerJourney) {
        return BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withProviderPassengerSectionId(ProviderPassengerSectionId.from(passengerJourney.getPassengerJourneyId()))
                .withStatus(new Status(
                        Status.Code.valueOf(passengerJourney.getStatus().toUpperCase(Locale.ENGLISH)),
                        null
                ))
                .withPassenger(mapPassenger(passenger))
                .withSection(mapSection(scheduledJourney))
                .build();
    }

    private Section mapSection(ScheduledJourney journey) {
        return Section.builder()
                .withId(SectionId.create())
                .withProviderSectionId(ProviderSectionId.from(journey.getId()))
                .withMarketingCarrier(Airline.create(journey.getAirline()))
                .withOperatingCarrier(Airline.create(journey.getAirline()))
                .withDepartureAirport(Airport.create(journey.getDepartureAirport()))
                .withArrivalAirport(Airport.create(journey.getArrivalAirport()))
                .withDepartureDate(journey.getDepartureDate())
                .withArrivalDate(journey.getArrivalDate())
                .withFlightNumber(journey.getFlightNumber())
                .withPnr(journey.getPnr())
                .build();
    }

    private Passenger mapPassenger(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.Passenger passenger) {
        return Passenger.builder()
                .withId(PassengerId.create())
                .withProviderPassengerId(ProviderPassengerId.from(passenger.getId()))
                .withName(passenger.getName())
                .withLastName(passenger.getLastName())
                .withDateOfBirth(passenger.getDateOfBirth())
                .withGender(mapGender(passenger.getGender()))
                .withNationality(passenger.getNationality())
                .withDocument(mapDocument(passenger.getDocument()))
                .build();
    }

    private Gender mapGender(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.Gender gender) {
        switch (gender) {
        case MALE:
            return Gender.M;
        case FEMALE:
            return Gender.F;
        default:
            return null;
        }
    }

    private Document mapDocument(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getcheckinstatus.model.Document document) {
        return Document.create(
                DocumentType.valueOf(document.getType().name()),
                document.getNumber(),
                document.getExpireDate(),
                document.getIssueDate(),
                document.getCountry()
        );
    }


}
