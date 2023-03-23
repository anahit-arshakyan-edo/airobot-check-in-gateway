package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey.Journey;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;

public class BoardingPass {

    private final BoardingPassId id;
    private final Delivery delivery;
    private final Passenger passenger;
    private final Journey journey;
    private final Status status;
    private final String url;
    private final Format format;
    private final Error error;

    public BoardingPass(BoardingPassId id, Delivery delivery, Passenger passenger, Journey journey, Status status, String url, Format format, Error error) {
        this.id = id;
        this.delivery = delivery;
        this.passenger = passenger;
        this.journey = journey;
        this.status = status;
        this.url = url;
        this.format = format;
        this.error = error;
    }

    public BoardingPassId id() {
        return id;
    }

    public Delivery delivery() {
        return delivery;
    }

    public Passenger passenger() {
        return passenger;
    }

    public Journey journey() {
        return journey;
    }

    public Status status() {
        return status;
    }

    public String url() {
        return url;
    }

    public Format format() {
        return format;
    }

    public Error error() {
        return error;
    }

    public boolean isFailed() {
        return this.status.equals(Status.FAILED);
    }

    public boolean isSuccess() {
        return this.status.equals(Status.SUCCESS);
    }

    public boolean isPending() {
        return this.status.equals(Status.PENDING);
    }
}
