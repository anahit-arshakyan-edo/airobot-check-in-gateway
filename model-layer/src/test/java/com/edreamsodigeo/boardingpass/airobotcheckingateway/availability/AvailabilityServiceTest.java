package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
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
        Passengers passengers = new Passengers();
        AvailabilityRequest availabilityRequest = requestOf(section, passengers);

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
        Passengers passengers = null;
        AvailabilityRequest availabilityRequest = requestOf(section, passengers);

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

    @Test
    public void returnAvailabilityResultForValidRequestWithTwoSections() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section firstSection = sectionOf("IB", "BCN", "MXP");
        Section secondSection = sectionOf("FR", "MXP", "BCN");
        Passengers passengers = new Passengers();
        AvailabilityRequest availabilityRequest = requestOf(List.of(firstSection, secondSection), passengers);

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertTrue(availabilityResult.isValidRequest());
        assertSame(availabilityResult.getAvailability(), returnedAvailability);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToEmptySectionList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Passengers passengers = new Passengers();
        AvailabilityRequest availabilityRequest = requestOf(Collections.emptyList(), passengers);

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertFalse(availabilityResult.isValidRequest());
        assertFalse(airobot.isInvoked());
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToOneNullSectionInTheList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(asList(sectionOf("IB", "BCN", "MXP"), null));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertFalse(availabilityResult.isValidRequest());
        assertFalse(airobot.isInvoked());
    }


    private Section sectionOf(String airline, String departure, String arrival) {
        return new Section(new Airline(airline), new Airport(departure), new Airport(arrival));
    }

    private AvailabilityRequest requestOf(Section section) {
        return requestOf(section != null ? List.of(section) : null, new Passengers());
    }

    private AvailabilityRequest requestOf(List<Section> sections) {
        return requestOf(sections, new Passengers());
    }

    private AvailabilityRequest requestOf(Section section, Passengers passengers) {
        return requestOf(List.of(section), passengers);
    }

    private AvailabilityRequest requestOf(List<Section> sections, Passengers passengers) {
        return new AvailabilityRequest(sections, passengers);
    }

}
