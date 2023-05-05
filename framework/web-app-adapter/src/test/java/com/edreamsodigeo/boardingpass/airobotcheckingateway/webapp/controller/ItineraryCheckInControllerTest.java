package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.ValidationException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status.GetCheckInStatusUseCase;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.AvailabilityRequestMother.AVAILABILITY;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.AvailabilityRequestMother.AVAILABILITY_REQUEST;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.AvailabilityRequestMother.CHECK_IN_AVAILABILITY_REQUEST_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.AvailabilityRequestMother.CHECK_IN_AVAILABILITY_REQUEST_NO_SECTION_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.AvailabilityRequestMother.CHECK_IN_AVAILABILITY_RESPONSE_DTO;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ItineraryCheckInControllerTest {

    @Mock
    private GetAvailabilityUseCase availabilityUseCase;
    @Mock
    private CreateCheckInUseCase createCheckInUseCase;
    @Mock
    private GetCheckInStatusUseCase getCheckInStatusUseCase;

    private CheckInController controller;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CheckInController(availabilityUseCase, createCheckInUseCase, getCheckInStatusUseCase);
    }

    @Test
    public void getAvailabilityReturnsAvailabilityResponse() {
        when(availabilityUseCase.getAvailability(AVAILABILITY_REQUEST)).thenReturn(AVAILABILITY);

        CheckInAvailabilityResponse response = controller.getCheckInAvailability(CHECK_IN_AVAILABILITY_REQUEST_DTO);

        assertEquals(response, CHECK_IN_AVAILABILITY_RESPONSE_DTO);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void throwsAnExceptionForAnInvalidRequestWithNoSection() {
        controller.getCheckInAvailability(CHECK_IN_AVAILABILITY_REQUEST_NO_SECTION_DTO);
    }
}
