package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation.Checker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Section {

    private final SectionId id;
    private ProviderSectionId providerSectionId;
    @NotNull
    private final Airline operatingCarrier;
    @NotNull
    private final Airport departureAirport;
    @NotNull
    private final Airport arrivalAirport;
    @NotNull
    private final LocalDateTime departureDate;
    @NotNull
    private final LocalDateTime arrivalDate;
    @NotNull
    private final Integer flightNumber;
    @NotBlank
    private final String pnr;

    private Section(SectionId id, ProviderSectionId providerSectionId, Airline operatingCarrier, Airport departureAirport, Airport arrivalAirport, LocalDateTime departureDate, LocalDateTime arrivalDate, Integer flightNumber, String pnr) {
        this.id = id;
        this.providerSectionId = providerSectionId;
        this.operatingCarrier = operatingCarrier;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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

    public Integer flightNumber() {
        return flightNumber;
    }

    public String pnr() {
        return pnr;
    }

    public ProviderSectionId providerSectionId() {
        return providerSectionId;
    }

    public Airline operatingCarrier() {
        return operatingCarrier;
    }

    public void setProviderSectionId(ProviderSectionId providerSectionId) {
        this.providerSectionId = providerSectionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        return operatingCarrier.equals(section.operatingCarrier) && departureAirport.equals(section.departureAirport) && arrivalAirport.equals(section.arrivalAirport) && departureDate.equals(section.departureDate) && arrivalDate.equals(section.arrivalDate) && flightNumber.equals(section.flightNumber) && pnr.equals(section.pnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operatingCarrier, departureAirport, arrivalAirport, departureDate, arrivalDate, flightNumber, pnr);
    }

    public static Section.SectionBuilder builder() {
        return new Section.SectionBuilder();
    }

    public static class SectionBuilder {

        private SectionId id;
        private ProviderSectionId providerSectionId;
        private Airline operatingCarrier;
        private Airport departureAirport;
        private Airport arrivalAirport;
        private LocalDateTime departureDate;
        private LocalDateTime arrivalDate;
        private Integer flightNumber;
        private String pnr;

        public SectionBuilder withId(SectionId id) {
            this.id = id;
            return this;
        }

        public SectionBuilder withProviderSectionId(ProviderSectionId providerSectionId) {
            this.providerSectionId = providerSectionId;
            return this;
        }

        public SectionBuilder withOperatingCarrier(Airline operatingCarrier) {
            this.operatingCarrier = operatingCarrier;
            return this;
        }

        public SectionBuilder withDepartureAirport(Airport departureAirport) {
            this.departureAirport = departureAirport;
            return this;
        }

        public SectionBuilder withArrivalAirport(Airport arrivalAirport) {
            this.arrivalAirport = arrivalAirport;
            return this;
        }

        public SectionBuilder withDepartureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate.truncatedTo(ChronoUnit.MINUTES);
            return this;
        }

        public SectionBuilder withArrivalDate(LocalDateTime arrivalDate) {
            this.arrivalDate = arrivalDate.truncatedTo(ChronoUnit.MINUTES);
            return this;
        }

        public SectionBuilder withFlightNumber(Integer flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public SectionBuilder withPnr(String pnr) {
            this.pnr = pnr;
            return this;
        }

        public Section build() {
            Section section = new Section(this.id, this.providerSectionId, this.operatingCarrier, this.departureAirport, this.arrivalAirport,
                    this.departureDate, this.arrivalDate, this.flightNumber, this.pnr);
            Checker.checkValidityWithJSR380(section);
            return section;
        }

    }
}
