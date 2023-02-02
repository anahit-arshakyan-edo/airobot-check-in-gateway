package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class AvailabilityServiceTest {

    @Test
    public void airobotAnswersWithAnAvailability() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = new Section("IB", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = new AvailabilityRequest(section);

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertSame(availabilityResult.getAvailability(), returnedAvailability);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingDeparture() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = new Section("IB", "", "MXP");
        AvailabilityRequest availabilityRequest = new AvailabilityRequest(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertTrue(availability.isInvalidRequest());
        assertFalse(airobot.isInvoked());
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {;
        AirobotMockThrowsException airobot = new AirobotMockThrowsException();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = new Section("IB", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = new AvailabilityRequest(section);

        availabilityService.getAvailability(availabilityRequest);
    }
}
