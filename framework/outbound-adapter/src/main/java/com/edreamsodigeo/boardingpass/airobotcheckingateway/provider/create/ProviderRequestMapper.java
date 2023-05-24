package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderPassengerSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.ProviderPassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.ProviderSectionId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.SectionId;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.CheckInData;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.PassengerJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.ScheduledJourney;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProviderRequestMapper {

    public void updateProviderRequest(ProviderRequest providerRequest, CreateCheckInResponse createCheckInResponse) {

        List<BoardingPass> providerRequestBoardingPasses = providerRequest.boardingPasses();


        CheckInData checkInData = createCheckInResponse.getData();
        List<BoardingPass> requestedBoardingPasses = new ArrayList<>();

        for (com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger : checkInData.getPassengers()) {
            for (ScheduledJourney journey : checkInData.getJourneys()) {
                PassengerJourney passengerJourneyToMap = mapPassengerJourney(checkInData.getPassengerJourneys(), passenger, journey);
                requestedBoardingPasses.add(mapBoardingPass(passenger, journey, passengerJourneyToMap));
            }
        }

        providerRequest.setProviderRequestId(ProviderRequestId.from(checkInData.getRequestId()));

        for (BoardingPass requestedBoardingPass : requestedBoardingPasses) {
            BoardingPass providerRequestBoardingPass = providerRequestBoardingPasses.stream().filter(boardingPass -> boardingPass.equals(requestedBoardingPass)).findAny().orElseThrow();
            providerRequestBoardingPass.setStatus(requestedBoardingPass.status());

            providerRequestBoardingPass.setProviderPassengerSectionId(requestedBoardingPass.providerPassengerSectionId());
            providerRequestBoardingPass.section().setProviderSectionId(requestedBoardingPass.section().providerSectionId());
            providerRequestBoardingPass.passenger().setProviderPassengerId(requestedBoardingPass.passenger().providerPassengerId());
        }

    }

    private PassengerJourney mapPassengerJourney(List<PassengerJourney> passengerJourneys, com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger, ScheduledJourney journey) {
        return passengerJourneys.stream()
                .filter((passengerJourney) -> passenger.getId() == passengerJourney.getPassengerId()
                        && journey.getId() == passengerJourney.getJourneyId())
                .findAny()
                .orElseThrow();
    }

    private BoardingPass mapBoardingPass(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger, ScheduledJourney scheduledJourney, PassengerJourney passengerJourney) {
        return BoardingPass.builder()
                .withId(BoardingPassId.create())
                .withSection(mapSection(scheduledJourney))
                .withPassenger(mapPassenger(passenger))
                .withStatus(new Status(
                        Status.Code.valueOf(passengerJourney.getStatus().toUpperCase(Locale.ENGLISH)),
                        null
                ))
                .withProviderPassengerSectionId(ProviderPassengerSectionId.from(passengerJourney.getPassengerJourneyId()))
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

    private Passenger mapPassenger(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Passenger passenger) {
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

    private Gender mapGender(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Gender gender) {
        switch (gender) {
        case MALE:
            return Gender.M;
        case FEMALE:
            return Gender.F;
        default:
            return null;
        }
    }

    private Document mapDocument(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.model.Document document) {
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
