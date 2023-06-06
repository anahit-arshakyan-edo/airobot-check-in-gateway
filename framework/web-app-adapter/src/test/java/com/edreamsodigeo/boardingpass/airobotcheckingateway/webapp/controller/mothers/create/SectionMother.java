package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create;

import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Section;

import java.time.LocalDateTime;

public class SectionMother {

    public static final Section SECTION = Section.builder().withArrivalAirport("BCN").withDepartureAirport("MAD").withArrivalDate(LocalDateTime.MAX).withDepartureDate(LocalDateTime.MIN).withPnr("AAA123").withFlightNumber("4321").withMarketingCarrier("FR").withOperatingCarrier("FR").build();

}
