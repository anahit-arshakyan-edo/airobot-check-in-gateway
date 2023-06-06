package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create;

import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Buyer;

public class BuyerMother {

    public static final String BUYER_EMAIL = "buyer@email.com";
    public static final Buyer BUYER = Buyer.builder().withEmail(BUYER_EMAIL).build();

}
