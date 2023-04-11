package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class GetAvailabilityUseCaseImplTest {
    private  static final Availability EXPECTED_AVAILABILITY = new Availability(true, true, emptyList());

    @Mock
    GetAvailabilityOutboundPort airobot;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void availabilityResultForRequestWithTwoSections() {
        when(airobot.getAvailability(any())).thenReturn(EXPECTED_AVAILABILITY);
        GetAvailabilityUseCaseImpl getAvailabilityUseCaseImpl = new GetAvailabilityUseCaseImpl(airobot);
        AvailabilityRequest availabilityRequest = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .withSection("FR", "MXP", "BCN")
                .build();

        Availability availability = getAvailabilityUseCaseImpl.getAvailability(availabilityRequest);

        assertSame(availability, EXPECTED_AVAILABILITY);
    }

    @Test
    public void availabilityResultForRequestWithNullPassenger() {
        when(airobot.getAvailability(any())).thenReturn(EXPECTED_AVAILABILITY);
        GetAvailabilityUseCaseImpl getAvailabilityUseCaseImpl = new GetAvailabilityUseCaseImpl(airobot);
        AvailabilityRequest availabilityRequest = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .withPassengers(null)
                .build();

        Availability availabilityResult = getAvailabilityUseCaseImpl.getAvailability(availabilityRequest);

        assertSame(availabilityResult, EXPECTED_AVAILABILITY);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {
        when(airobot.getAvailability(any())).thenThrow(new RuntimeException());
        GetAvailabilityUseCaseImpl getAvailabilityUseCaseImpl = new GetAvailabilityUseCaseImpl(airobot);
        AvailabilityRequest availabilityRequest = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .build();

        getAvailabilityUseCaseImpl.getAvailability(availabilityRequest);
    }
}
