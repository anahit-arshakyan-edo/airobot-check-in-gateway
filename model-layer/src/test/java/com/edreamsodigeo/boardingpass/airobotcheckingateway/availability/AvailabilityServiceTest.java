package com.edreamsodigeo.boardingpass.airobotcheckingateway.availability;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityAsserts.assertExpectedAvailabilityIsReturned;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityAsserts.assertRequestIsInvalidAndAirobotIsNotInvoked;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityRequestHelper.requestOf;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.availability.helpers.AvailabilityRequestHelper.sectionOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AvailabilityServiceTest {
    @Mock
    Airobot airobot;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void availabilityResultForRequestWithTwoSections() {
        Availability returnedAvailability = new Availability();
        when(airobot.getAvailability(any())).thenReturn(returnedAvailability);

        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(List.of(
                sectionOf("IB", "BCN", "MXP"),
                sectionOf("FR", "MXP", "BCN")));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertExpectedAvailabilityIsReturned(availabilityResult, returnedAvailability);
    }

    @Test
    public void availabilityResultForRequestWithNullPassenger() {
        Availability returnedAvailability = new Availability();
        when(airobot.getAvailability(any())).thenReturn(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Passengers passengers = null;
        AvailabilityRequest availabilityRequest = requestOf(
                List.of(sectionOf("IB", "BCN", "MXP")), passengers);

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertExpectedAvailabilityIsReturned(availabilityResult, returnedAvailability);
    }

    @Test
    public void invalidAvailabilityRequestDueToNullSectionList() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        List<Section> nullSectionList = null;
        AvailabilityRequest availabilityRequest = requestOf(nullSectionList);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToEmptySectionList() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(emptyList());

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToOneNullSectionInTheList() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(asList(
                sectionOf("IB", "BCN", "MXP"), null));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToMissingAirline() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("", "LSB", "MXP"));

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToMissingDeparture() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", "", "MXP"));

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToMissingArrival() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", "MXP", ""));

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToOneInvalidSectionInTheList() {
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section validSection = sectionOf("IB", "BCN", "MXP");
        Section invalidSection = sectionOf("", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(asList(validSection, invalidSection));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {
        when(airobot.getAvailability(any())).thenThrow(new RuntimeException());
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", "BCN", "MXP"));

        availabilityService.getAvailability(availabilityRequest);
    }
}
