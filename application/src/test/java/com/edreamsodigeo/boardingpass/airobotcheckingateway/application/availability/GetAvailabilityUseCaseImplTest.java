package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreams.configuration.ConfigurationEngine;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.google.inject.AbstractModule;
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
    GetAvailabilityUseCase getAvailabilityUseCase;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ConfigurationEngine.init(new AbstractModule() {
            @Override
            protected void configure() {
                bind(GetAvailabilityUseCase.class).toInstance(new GetAvailabilityUseCaseImpl());
                bind(GetAvailabilityOutboundPort.class).toInstance(airobot);
            }
        });
        getAvailabilityUseCase = ConfigurationEngine.getInstance(GetAvailabilityUseCase.class);
        ((GetAvailabilityUseCaseImpl) getAvailabilityUseCase).init();
    }

    @Test
    public void availabilityResultForRequestWithTwoSections() {
        when(airobot.getAvailability(any())).thenReturn(EXPECTED_AVAILABILITY);
        AvailabilityRequest availabilityRequest = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .withSection("FR", "MXP", "BCN")
                .build();

        Availability availability = getAvailabilityUseCase.getAvailability(availabilityRequest);

        assertSame(availability, EXPECTED_AVAILABILITY);
    }

    @Test
    public void availabilityResultForRequestWithNullPassenger() {
        when(airobot.getAvailability(any())).thenReturn(EXPECTED_AVAILABILITY);
        AvailabilityRequest availabilityRequest = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .withPassengers(null)
                .build();

        Availability availabilityResult = getAvailabilityUseCase.getAvailability(availabilityRequest);

        assertSame(availabilityResult, EXPECTED_AVAILABILITY);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void relaunchAnExceptionThrownByAirobot() {
        when(airobot.getAvailability(any())).thenThrow(new RuntimeException());
        AvailabilityRequest availabilityRequest = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .build();

        getAvailabilityUseCase.getAvailability(availabilityRequest);
    }
}
