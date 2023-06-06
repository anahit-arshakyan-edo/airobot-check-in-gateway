package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

public class Status {

    private final Code code;
    private final Reason reason;

    public Status(Code code) {
        this(code, null);
    }

    public Status(Code code, Reason reason) {
        this.code = code;
        this.reason = reason;
    }

    public Code code() {
        return code;
    }

    public Reason reason() {
        return reason;
    }

    public enum Code {
        INITIALIZED("initialized"),
        PENDING("pending"),
        SUCCEED("succeed"),
        FAILED("failed");

        private final String description;

        Code(String description) {
            this.description = description;
        }

        public String description() {
            return description;
        }
    }

    public enum Reason {
        BOOKING_NOT_FOUND("BookingNotFound"),
        PASSENGER_NOT_FOUND("PassengerNotFound"),
        MISSING_TRAVEL_DOCUMENT_INFO("MissingTravelDocumentInfo"),
        FLIGHT_CANCELED("FlightCanceled"),
        FLIGHT_ALREADY_FLOWN("FlightAlreadyFlown"),
        ONLY_AIRPORT_CHECK_IN("OnlyCheckinAeroport"),
        OTHER("GeneralError");

        private final String description;

        Reason(String description) {
            this.description = description;
        }

        public String description() {
            return description;
        }
    }
}
