package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AirobotMock;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AirobotMockThrowsException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.NotInvokedAirobotMock;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityAsserts.assertExpectedAvailabilityIsReturned;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityAsserts.assertRequestIsInvalidAndAirobotIsNotInvoked;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityRequestHelper.requestOf;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityRequestHelper.sectionOf;
import static java.util.Arrays.asList;

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

        assertExpectedAvailabilityIsReturned(availabilityResult, returnedAvailability);
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

        assertExpectedAvailabilityIsReturned(availabilityResult, returnedAvailability);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToNullSection() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section nullSection = null;
        AvailabilityRequest availabilityRequest = requestOf(nullSection);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingAirline() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("", "LSB", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingDeparture() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToMissingArrival() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "MXP", "");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
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
        AvailabilityRequest availabilityRequest = requestOf(List.of(firstSection, secondSection));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertExpectedAvailabilityIsReturned(availabilityResult, returnedAvailability);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToEmptySectionList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(Collections.emptyList());

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToOneNullSectionInTheList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(asList(sectionOf("IB", "BCN", "MXP"), null));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test
    public void returnInvalidAvailabilityRequestDueToOneInvalidSectionInTheList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section validSection = sectionOf("IB", "BCN", "MXP");
        Section invalidSection = sectionOf("", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(asList(validSection, invalidSection));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }


}
