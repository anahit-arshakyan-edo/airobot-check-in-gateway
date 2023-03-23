package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;

import java.time.LocalDateTime;

public class Journey {

    private final JourneyId id;
    private final Airline airline;
    private final Airport departureAirport;
    private final Airport arrivalAirport;
    private final LocalDateTime arrivalDate;
    private final LocalDateTime departureDate;
    private final int flightNumber;
    private final int bookingId;
    private final String pnr;
    private final String ticketNumber;

    public Journey(JourneyId id, Airline airline, Airport departureAirport, Airport arrivalAirport, LocalDateTime arrivalDate, LocalDateTime departureDate, int flightNumber, int bookingId, String pnr, String ticketNumber) {
        this.id = id;
        this.airline = airline;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.flightNumber = flightNumber;
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.ticketNumber = ticketNumber;
    }

    public JourneyId id() {
        return id;
    }

    public Airline airline() {
        return airline;
    }

    public Airport departureAirport() {
        return departureAirport;
    }

    public Airport arrivalAirport() {
        return arrivalAirport;
    }

    public LocalDateTime arrivalDate() {
        return arrivalDate;
    }

    public LocalDateTime departureDate() {
        return departureDate;
    }

    public int flightNumber() {
        return flightNumber;
    }

    public int bookingId() {
        return bookingId;
    }

    public String pnr() {
        return pnr;
    }

    public String ticketNumber() {
        return ticketNumber;
    }
}
