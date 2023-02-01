package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

public class AvailabilityServiceTest {

    @Test
    public void airobotAnswersWithAnAvailability() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = new AvailabilityRequest();

        Availability availability = availabilityService.getAvailability(availabilityRequest);

        assertSame(availability, returnedAvailability);
    }
}
