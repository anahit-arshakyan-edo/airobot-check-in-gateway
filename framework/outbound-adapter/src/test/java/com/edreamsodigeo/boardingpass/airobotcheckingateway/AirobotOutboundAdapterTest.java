package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.AirobotApiConfiguration;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.BoardingPassDeliveryCustomization;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Gender;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.availability.GetAvailabilityAirobotApiOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create.RequestCheckInAirobotApiOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.BadRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.AvailabilityTestObjectMother.AVAILABILITY;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.AvailabilityTestObjectMother.AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.AvailabilityTestObjectMother.AVAILABILITY_REQUEST;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.AvailabilityTestObjectMother.AVAILABILITY_RESPONSE_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.AvailabilityTestObjectMother.AVAILABILITY_RESPONSE_PERMITTED_DOCUMENTS_EMPTY_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.CreateCheckInTestObjectMother.CREATE_CHECK_IN_RESPONSE_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.CreateCheckInTestObjectMother.itineraryCheckIn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

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
        GetAvailabilityOutboundPort airobotOutboundAdapter = new GetAvailabilityAirobotApiOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.getFlightAvailability(eq(API_TOKEN), any())).thenReturn(AVAILABILITY_RESPONSE_DTO);

        Availability availability = airobotOutboundAdapter.getAvailability(AVAILABILITY_REQUEST);

        assertEquals(availability, AVAILABILITY);
    }

    @Test
    public void getAvailabilityReturnsResponseWithNoPermittedDocuments() {
        GetAvailabilityOutboundPort airobotOutboundAdapter = new GetAvailabilityAirobotApiOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.getFlightAvailability(eq(API_TOKEN), any())).thenReturn(AVAILABILITY_RESPONSE_PERMITTED_DOCUMENTS_EMPTY_DTO);

        Availability availability = airobotOutboundAdapter.getAvailability(AVAILABILITY_REQUEST);

        assertEquals(availability, AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY);
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void availabilityApiCallWithInvalidInput() {
        GetAvailabilityOutboundPort airobotOutboundAdapter = new GetAvailabilityAirobotApiOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.getFlightAvailability(eq(API_TOKEN), any())).thenThrow(BadRequestException.class);

        airobotOutboundAdapter.getAvailability(AVAILABILITY_REQUEST);
    }

    @Test
    public void requestCheckInReturnsValidResponse() {
        RequestCheckInOutboundPort requestCheckInOutboundPort = new RequestCheckInAirobotApiOutboundAdapter(airobotResource, airobotApiConfiguration);
        when(airobotResource.createCheckIn(eq(API_TOKEN), any())).thenReturn(CREATE_CHECK_IN_RESPONSE_DTO);

        ItineraryCheckIn itineraryCheckIn = itineraryCheckIn();

        SegmentCheckIn outboundSegmentCheckIn = itineraryCheckIn.segmentCheckIns().stream()
                .filter(segmentCheckIn -> "MXP".equals(segmentCheckIn.boardingPasses().get(0).section().departureAirport().iataCode()))
                .findFirst().orElseThrow();

        SegmentCheckIn requestedSegmentCheckIn = requestCheckInOutboundPort.request(itineraryCheckIn.referenceId(), outboundSegmentCheckIn);

        assertNotNull(requestedSegmentCheckIn);

        BoardingPassDeliveryCustomization boardingPassDeliveryCustomization = requestedSegmentCheckIn.boardingPassDeliveryCustomization();

        assertEquals("jon@doe.com", boardingPassDeliveryCustomization.bookingEmail());
        assertEquals("jon@doe.com", boardingPassDeliveryCustomization.deliveryEmail());
        assertEquals("ES", boardingPassDeliveryCustomization.country());
        assertEquals("es_ES", boardingPassDeliveryCustomization.language());
        assertEquals("EDREAMS", boardingPassDeliveryCustomization.brand());

        assertEquals(ProviderRequestId.from("12345ABCDEF"), requestedSegmentCheckIn.providerRequestId());

        List<Passenger> passengers = requestedSegmentCheckIn.passengers();
        assertNotNull(passengers);

        Passenger passenger = passengers.get(0);
        assertNotNull(passenger);

        assertEquals("Jon", passenger.name());
        assertEquals("Doe", passenger.lastName());
        assertEquals("ES", passenger.nationality());
        assertEquals(Gender.M, passenger.gender());
        assertEquals(LocalDate.of(1980, 2, 1), passenger.dateOfBirth());

        Document document = passenger.document();
        assertNotNull(document);

        assertEquals(DocumentType.PASSPORT, document.type());
        assertEquals("123456", document.number());
        assertEquals("ES", document.country());
        assertEquals(LocalDate.of(2020, 10,  1), document.issueDate());
        assertEquals(LocalDate.of(2025, 10,  1), document.expireDate());

        List<Section> sections = requestedSegmentCheckIn.sections();
        assertNotNull(sections);

        Section section = sections.get(0);
        assertNotNull(section);

        assertEquals(Airport.create("MXP"), section.departureAirport());
        assertEquals(Airport.create("BCN"), section.arrivalAirport());
        assertEquals(LocalDateTime.of(2023, 2, 1, 22, 0), section.departureDate());
        assertEquals(LocalDateTime.of(2023, 2, 1, 23, 0), section.arrivalDate());
        assertEquals(Airline.create("FR"), section.marketingCarrier());
        assertEquals(Airline.create("FR"), section.operatingCarrier());
        assertEquals(Integer.valueOf(1234), section.flightNumber());
        assertEquals("ABC123", section.pnr());

    }

}
