package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create;

import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;

import java.util.List;

public class CreateCheckInRequestMother {

    public static final CreateCheckInRequest CREATE_CHECKIN_REQUEST_WITH_VIRTUAL_EMAIL = CreateCheckInRequest.builder().withItinerary(ItineraryMother.ITINERARY_WITH_VIRTUAL_EMAIL).withBuyer(BuyerMother.BUYER).withLocale("es_ES").withPassengers(List.of(PassengerMother.PASSENGER)).withWebsite(WebsiteMother.ED_ES).build();


}
