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
    public void availabilityResultForRequestWithTwoSections() {
        Availability returnedAvailability = new Availability();
        AirobotMock airobot = new AirobotMock(returnedAvailability);
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section1 = sectionOf("IB", "BCN", "MXP");
        Section section2 = sectionOf("FR", "MXP", "BCN");
        AvailabilityRequest availabilityRequest = requestOf(List.of(section1, section2));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertExpectedAvailabilityIsReturned(availabilityResult, returnedAvailability);
    }


    @Test
    public void availabilityResultForRequestWithNullPassenger() {
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
    public void invalidAvailabilityRequestDueToNullSectionList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        List<Section> nullSectionList = null;
        AvailabilityRequest availabilityRequest = requestOf(nullSectionList);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToEmptySectionList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(Collections.emptyList());

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToOneNullSectionInTheList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        AvailabilityRequest availabilityRequest = requestOf(asList(
                sectionOf("IB", "BCN", "MXP"), null));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }


    @Test
    public void invalidAvailabilityRequestDueToMissingAirline() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("", "LSB", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToMissingDeparture() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToMissingArrival() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "MXP", "");
        AvailabilityRequest availabilityRequest = requestOf(section);

        AvailabilityResult availability = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availability, airobot);
    }

    @Test
    public void invalidAvailabilityRequestDueToOneInvalidSectionInTheList() {
        NotInvokedAirobotMock airobot = new NotInvokedAirobotMock();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section validSection = sectionOf("IB", "BCN", "MXP");
        Section invalidSection = sectionOf("", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(asList(validSection, invalidSection));

        AvailabilityResult availabilityResult = availabilityService.getAvailability(availabilityRequest);

        assertRequestIsInvalidAndAirobotIsNotInvoked(availabilityResult, airobot);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {;
        AirobotMockThrowsException airobot = new AirobotMockThrowsException();
        AvailabilityService availabilityService = new AvailabilityService(airobot);
        Section section = sectionOf("IB", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(section);

        availabilityService.getAvailability(availabilityRequest);
    }


}
