package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create.CreateCheckInRequestMother;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CreateCheckInRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create.BuyerMother.BUYER_EMAIL;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create.ItineraryMother.VIRTUAL_EMAIL;

public class ItineraryCheckInMapperTest {

    private ItineraryCheckInMapper mapper = new ItineraryCheckInMapper();

    @Test
    public void testWithDifferentBookingEmail() {
        CreateCheckInRequest request = CreateCheckInRequestMother.CREATE_CHECKIN_REQUEST_WITH_VIRTUAL_EMAIL;

        ItineraryCheckIn response = mapper.map(request);

        Assert.assertEquals(response.providerRequests().size(), 1);
        Assert.assertEquals(response.providerRequests().get(0).deliveryOptions().bookingEmail(), VIRTUAL_EMAIL);
        Assert.assertEquals(response.providerRequests().get(0).deliveryOptions().deliveryEmail(), BUYER_EMAIL);
    }

}
