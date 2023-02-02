package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class AvailabilityServiceTest {

    @Test
    public void airobotAnswersWithAnAvailability() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = new AvailabilityRequest("BCN");

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertSame(availabilityResult.getAvailability(), returnedAvailability);
    }

    @Test
    public void returnInvalidAvailabilityRequest() {
        AvailabilityService availabilityService = new AvailabilityService(null);
        String departureAirport = "";
        AvailabilityRequest availabilityRequest = new AvailabilityRequest(departureAirport);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertTrue(availability.isInvalidRequest());

    }
}
