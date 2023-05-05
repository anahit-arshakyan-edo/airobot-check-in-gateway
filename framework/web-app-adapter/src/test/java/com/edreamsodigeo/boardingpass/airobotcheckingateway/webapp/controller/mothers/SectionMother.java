package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.SectionBuilder;

public class SectionMother {
    public static final com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section SECTION_DTO =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section.builder()
                    .withMarketingCarrier("IB")
                    .withOperatingCarrier("IB")
                    .withDepartureAirport("BCN")
                    .withArrivalAirport("MAD")
                    .build();

    public static final Section SECTION = new SectionBuilder()
            .withAirline("IB")
            .withDeparture("BCN")
            .withArrival("MAD")
            .build();
}
