package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create;



import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Passenger;

import java.time.LocalDate;


public class PassengerMother {

    public static final Passenger PASSENGER = Passenger.builder().withEmail(BuyerMother.BUYER_EMAIL).withDateOfBirth(LocalDate.EPOCH).withName("Juan").withMiddleName("Carlos").withLastName("Rodriguez").build();

}
