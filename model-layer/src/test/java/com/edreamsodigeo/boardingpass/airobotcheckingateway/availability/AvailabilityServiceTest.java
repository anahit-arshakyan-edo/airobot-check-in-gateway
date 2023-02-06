package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class AvailabilityServiceTest {

    @Test
    public void returnAvailabilityResultForValidRequest() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "BCN", "MXP");
        Passenger passenger = new Passenger();
        AvailabilityRequest availabilityRequest = requestOf(section, passenger);

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertTrue(availabilityResult.isValidRequest());
        assertSame(availabilityResult.getAvailability(), returnedAvailability);
    }

    @Test
    public void returnResultForAvailabilityRequestWithNullPassenger() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "BCN", "MXP");
        Passenger passenger = null;
        AvailabilityRequest availabilityRequest = requestOf(section, passenger);

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertTrue(availabilityResult.isValidRequest());
        assertSame(availabilityResult.getAvailability(), returnedAvailability);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToNullSection() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section nullSection = null;
        AvailabilityRequest availabilityRequest = requestOf(nullSection);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertFalse(availability.isValidRequest());
        assertFalse(airobot.isInvoked());
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingAirline() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("", "LSB", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertFalse(availability.isValidRequest());
        assertFalse(airobot.isInvoked());
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingDeparture() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertFalse(availability.isValidRequest());
        assertFalse(airobot.isInvoked());
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingArrival() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "MXP", "");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertFalse(availability.isValidRequest());
        assertFalse(airobot.isInvoked());
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {;
        AirobotMockThrowsException airobot = new AirobotMockThrowsException();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        availabilityService.getAvailability(availabilityRequest);
    }


    private Section sectionOf(String airline, String departure, String arrival) {
        return new Section(new Airline(airline), new Airport(departure), new Airport(arrival));
    }

    private AvailabilityRequest requestOf(Section section) {
        return new AvailabilityRequest(section != null ? List.of(section) : null);
    }

    private AvailabilityRequest requestOf(Section section, Passenger passenger) {
        return new AvailabilityRequest(List.of(section), passenger);
    }

}
