package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;

import java.time.LocalDateTime;
import java.util.Objects;

public class Section {

    private final SectionId id;
    private final ProviderSectionId providerSectionId;
    private final Airline marketingCarrier;
    private final Airline operatingCarrier;
    private final Airport departureAirport;
    private final Airport arrivalAirport;
    private final LocalDateTime arrivalDate;
    private final LocalDateTime departureDate;
    private final int flightNumber;
    private final String pnr;

    public Section(SectionId id, ProviderSectionId providerSectionId, Airline marketingCarrier, Airline operatingCarrier, Airport departureAirport, Airport arrivalAirport, LocalDateTime arrivalDate, LocalDateTime departureDate, int flightNumber, String pnr) {
        this.id = id;
        this.providerSectionId = providerSectionId;
        this.marketingCarrier = marketingCarrier;
        this.operatingCarrier = operatingCarrier;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.flightNumber = flightNumber;
        this.pnr = pnr;
    }

    public SectionId id() {
        return id;
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

    public String pnr() {
        return pnr;
    }

    public ProviderSectionId providerSectionId() {
        return providerSectionId;
    }

    public Airline marketingCarrier() {
        return marketingCarrier;
    }

    public Airline operatingCarrier() {
        return operatingCarrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return id.equals(section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
