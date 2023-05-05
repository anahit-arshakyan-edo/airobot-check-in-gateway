package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
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

    private Status mapStatus(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Status status) {

        Status.Code statusCode = null;
        Status.Reason statusReason = null;


        switch (status.code()) {
        case FAILED:
            statusCode = Status.Code.FAILED;
            break;
        case PENDING: case INITIALIZED:
            statusCode = Status.Code.PENDING;
            break;
        case SUCCEED:
            statusCode = Status.Code.SUCCESSFUL;
            break;
        }

        if (status.reason() != null) {
            switch (status.reason()) {
            case BOOKING_NOT_FOUND:
                statusReason = Status.Reason.BOOKING_NOT_FOUND;
                break;
            case PASSENGER_NOT_FOUND:
                statusReason = Status.Reason.PASSENGER_NOT_FOUND;
                break;
            case MISSING_TRAVEL_DOCUMENT_INFO:
                statusReason = Status.Reason.MISSING_TRAVEL_DOCUMENT_INFO;
                break;
            case FLIGHT_CANCELED:
                statusReason = Status.Reason.FLIGHT_CANCELED;
                break;
            case FLIGHT_ALREADY_FLOWN:
                statusReason = Status.Reason.FLIGHT_ALREADY_FLOWN;
                break;
            case ONLY_AIRPORT_CHECK_IN:
                statusReason = Status.Reason.ONLY_AIRPORT_CHECK_IN;
                break;
            case OTHER:
                statusReason = Status.Reason.OTHER;
                break;
            }
        }

        return Status.builder()
                .withCode(statusCode)
                .withReason(statusReason)
                .build();
    }

    private Section mapSection(com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section section) {
        return Section.builder()
                .withDepartureAirport(section.departureAirport().iataCode())
                .withArrivalAirport(section.arrivalAirport().iataCode())
                .withDepartureDate(section.departureDate())
                .withArrivalDate(section.arrivalDate())
                .withMarketingCarrier(section.marketingCarrier().iataCode())
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
