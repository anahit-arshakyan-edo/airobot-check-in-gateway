package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

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
        SUCCEED,
        FAILED,
        PENDING
    }

    public enum Reason {
        BOOKING_NOT_FOUND,
        PASSENGER_NOT_FOUND,
        MISSING_TRAVEL_DOCUMENT_INFO,
        FLIGHT_CANCELED,
        FLIGHT_ALREADY_FLOWN,
        ONLY_AIRPORT_CHECK_IN,
        OTHER
    }
}
