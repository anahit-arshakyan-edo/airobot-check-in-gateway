package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityUseCase;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class CheckInControllerTest {

    @Mock
    private AvailabilityUseCase availabilityUseCase;

    private CheckInController controller;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CheckInController(availabilityUseCase);
    }

    @Test
    public void getAvailabilityReturnsAvailabilityResponse() {
        Availability availability = TestObjectMother.AVAILABILITY;
        when(availabilityUseCase.getAvailability(any())).thenReturn(availability);
        CheckInAvailabilityRequest request = buildAvailabilityRequest("IB", "BCN", "MAD");
        CheckInAvailabilityResponse expectedResponse = TestObjectMother.AVAILABILTY_RESPONSE;

        CheckInAvailabilityResponse response = controller.getCheckInAvailability(request);

        assertEquals(response, expectedResponse);
    }

    @Test
    public void getAvailabilityReturnsAvailabilityFalse() {
        Availability availability = TestObjectMother.AVAILABILITY_WITH_FALSE_RESULT;
        when(availabilityUseCase.getAvailability(any())).thenReturn(availability);
        CheckInAvailabilityRequest request = buildAvailabilityRequest("IB", "BCN", "MAD");

        CheckInAvailabilityResponse response = controller.getCheckInAvailability(request);

        verify(availabilityUseCase).getAvailability(any());
        assertFalse(response.isAvailable());
    }

    private CheckInAvailabilityRequest buildAvailabilityRequest(String carrier, String departure, String arrival) {
        List<Section> sections = Collections.singletonList(Section.builder()
                .withDepartureAirport(departure)
                .withArrivalAirport(arrival)
                .withMarketingCarrier(carrier)
                .withOperatingCarrier(carrier)
                .build());

        return CheckInAvailabilityRequest.builder()
                .withSections(sections)
                .build();
    }

}
