package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create;

import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Itinerary;

import java.util.List;

public class ItineraryMother {

    public static final String VIRTUAL_EMAIL = "virtual@email.com";
    public static final Itinerary ITINERARY_WITH_VIRTUAL_EMAIL =  Itinerary.builder().withBookingEmail(VIRTUAL_EMAIL).withSegments(List.of(SegmentMother.SEGMENT)).build();

}
