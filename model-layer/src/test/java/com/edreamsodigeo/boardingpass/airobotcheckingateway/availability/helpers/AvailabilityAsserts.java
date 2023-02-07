package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.AvailabilityResult;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class AvailabilityAsserts {
    public static void assertExpectedAvailabilityIsReturned(AvailabilityResult availabilityResult, Availability returnedAvailability) {
        assertTrue(availabilityResult.isValidRequest());
        assertSame(availabilityResult.getAvailability(), returnedAvailability);
    }

    public static void assertRequestIsInvalidAndAirobotIsNotInvoked(AvailabilityResult availabilityResult, NotInvokedAirobotMock airobot) {
        assertFalse(availabilityResult.isValidRequest());
        assertFalse(airobot.isInvoked());
    }
}
