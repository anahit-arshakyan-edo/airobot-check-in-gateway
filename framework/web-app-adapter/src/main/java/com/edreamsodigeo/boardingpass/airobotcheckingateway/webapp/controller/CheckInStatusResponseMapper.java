package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.getcheckinstatus.CheckInStatus;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.getcheckinstatus.CheckInStatusResponse;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.getcheckinstatus.Passenger;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.getcheckinstatus.Section;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.getcheckinstatus.Status;

import java.util.ArrayList;
import java.util.List;

public class CheckInStatusResponseMapper {

    public CheckInStatusResponse map(ItineraryCheckIn itineraryCheckIn) {

        List<CheckInStatus> checkInStatuses = new ArrayList<>();

        for (BoardingPass boardingPass: itineraryCheckIn.boardingPasses()) {

            CheckInStatus checkInStatus = CheckInStatus.builder()
                    .withStatus(mapStatus(boardingPass.status()))
                    .withBoardingPassId(boardingPass.id().value())
                    .withSection(mapSection(boardingPass.section()))
                    .withPassenger(mapPassenger(boardingPass.passenger()))
                    .build();

            checkInStatuses.add(checkInStatus);
        }

        return CheckInStatusResponse.builder()
                .withCheckInStatuses(checkInStatuses)
                .build();
    }

    private Status mapStatus(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.Status status) {

        Status.Code statusCode = null;

        switch (status.code()) {
        case FAILED:
            statusCode = Status.Code.FAILED;
            break;
        case PENDING:
        case INITIALIZED:
            statusCode = Status.Code.PENDING;
            break;
        case SUCCEED:
            statusCode = Status.Code.SUCCEED;
            break;
        }

        return Status.builder()
                .withCode(statusCode)
                .build();
    }

    private Section mapSection(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section section) {
        return Section.builder()
                .withDepartureAirport(section.departureAirport().iataCode())
                .withArrivalAirport(section.arrivalAirport().iataCode())
                .withDepartureDate(section.departureDate())
                .withArrivalDate(section.arrivalDate())
                .withMarketingCarrier(null)
                .withOperatingCarrier(section.operatingCarrier().iataCode())
                .withFlightNumber(section.flightNumber())
                .withPnr(section.pnr())
                .build();
    }

    private Passenger mapPassenger(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger passenger) {
        return Passenger.builder()
                .withName(passenger.name())
                .withLastName(passenger.lastName())
                .withDateOfBirth(passenger.dateOfBirth())
                .build();
    }

}
