package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.AirobotOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.BadRequestException;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.TestObjectMother.AVAILABILITY;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.TestObjectMother.AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.TestObjectMother.AVAILABILITY_REQUEST;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.TestObjectMother.AVAILABILITY_RESPONSE_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.TestObjectMother.AVAILABILITY_RESPONSE_PERMITTED_DOCUMENTS_EMPTY_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class AirobotOutboundAdapterTest {

    public static final String API_TOKEN = "token";
    @Mock
    AirobotResource airobotResource;
    AirobotApiConfiguration airobotApiConfiguration = new AirobotApiConfiguration();

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        airobotApiConfiguration.setApiToken(API_TOKEN);
    }

    @Test
    public void getAvailabilityReturnsValidResponse() {
        AirobotOutboundPort airobotOutboundAdapter = new AirobotOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.getFlightAvailability(eq(API_TOKEN), any())).thenReturn(AVAILABILITY_RESPONSE_DTO);

        Availability availability = airobotOutboundAdapter.getAvailability(AVAILABILITY_REQUEST);

        assertEquals(availability, AVAILABILITY);
    }

    @Test
    public void getAvailabilityReturnsResponseWithNoPermittedDocuments() {
        AirobotOutboundPort airobotOutboundAdapter = new AirobotOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.getFlightAvailability(eq(API_TOKEN), any())).thenReturn(AVAILABILITY_RESPONSE_PERMITTED_DOCUMENTS_EMPTY_DTO);

        Availability availability = airobotOutboundAdapter.getAvailability(AVAILABILITY_REQUEST);

        assertEquals(availability, AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY);
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void availabilityApiCallWithInvalidInput() {
        AirobotOutboundPort airobotOutboundAdapter = new AirobotOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.getFlightAvailability(eq(API_TOKEN), any())).thenThrow(BadRequestException.class);

        airobotOutboundAdapter.getAvailability(AVAILABILITY_REQUEST);
    }

}
