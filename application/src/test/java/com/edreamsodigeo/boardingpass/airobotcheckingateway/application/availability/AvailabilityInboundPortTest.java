package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.helpers.AvailabilityRequestHelper.requestOf;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.helpers.AvailabilityRequestHelper.sectionOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class AvailabilityInboundPortTest {
    @Mock
    AirobotOutboundPort airobot;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void availabilityResultForRequestWithTwoSections() {
        Availability expectedAvailability = new Availability();
        when(airobot.getAvailability(any())).thenReturn(expectedAvailability);

        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(List.of(
                sectionOf("IB", "BCN", "MXP"),
                sectionOf("FR", "MXP", "BCN")));

        Availability availability = availabilityInboundPort.getAvailability(availabilityRequest);

        assertSame(availability, expectedAvailability);
    }

    @Test
    public void availabilityResultForRequestWithNullPassenger() {
        Availability expectedAvailability = new Availability();
        when(airobot.getAvailability(any())).thenReturn(expectedAvailability);
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        Passengers passengers = null;
        AvailabilityRequest availabilityRequest = requestOf(
                List.of(sectionOf("IB", "BCN", "MXP")), passengers);

        Availability availabilityResult = availabilityInboundPort.getAvailability(availabilityRequest);

        assertSame(availabilityResult, expectedAvailability);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToNullSectionList() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        List<Section> nullSectionList = null;
        AvailabilityRequest availabilityRequest = requestOf(nullSectionList);

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToEmptySectionList() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(emptyList());

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToOneNullSectionInTheList() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(asList(
                sectionOf("IB", "BCN", "MXP"), null));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToMissingAirline() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("", "LSB", "MXP"));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToMissingDeparture() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", "", "MXP"));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToMissingArrival() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", "MXP", ""));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityRequestDueToOneInvalidSectionInTheList() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        Section validSection = sectionOf("IB", "BCN", "MXP");
        Section invalidSection = sectionOf("", "BCN", "MXP");
        AvailabilityRequest availabilityRequest = requestOf(asList(validSection, invalidSection));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {
        when(airobot.getAvailability(any())).thenThrow(new RuntimeException());
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", "BCN", "MXP"));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

    @Test(expectedExceptions = InvalidAvailabilityRequestException.class)
    public void invalidAvailabilityThrowBadRequestException() {
        AvailabilityInboundPort availabilityInboundPort = new AvailabilityInboundPort(airobot);
        AvailabilityRequest availabilityRequest = requestOf(sectionOf("IB", null, "MXP"));

        availabilityInboundPort.getAvailability(availabilityRequest);
    }

}
