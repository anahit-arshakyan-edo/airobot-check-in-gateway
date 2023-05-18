package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

import com.edreams.configuration.ConfigurationEngine;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.CheckInTestObjectMother;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInStatusOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.google.inject.AbstractModule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

public class GetCheckInStatusUseCaseTest {

    @Mock
    GetCheckInOutboundPort getCheckInOutboundPort;
    @Mock
    GetCheckInStatusOutboundPort getCheckInStatusOutboundPort;
    GetCheckInStatusUseCase getCheckInStatusUseCase;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ConfigurationEngine.init(new AbstractModule() {
            @Override
            protected void configure() {
                bind(GetCheckInStatusUseCase.class).toInstance(new GetCheckInStatusUseCaseImpl());
                bind(GetCheckInOutboundPort.class).toInstance(getCheckInOutboundPort);
                bind(GetCheckInStatusOutboundPort.class).toInstance(getCheckInStatusOutboundPort);
            }
        });
        getCheckInStatusUseCase = ConfigurationEngine.getInstance(GetCheckInStatusUseCaseImpl.class);
        ((GetCheckInStatusUseCaseImpl) getCheckInStatusUseCase).init();
    }

    @Test
    public void oneWayWithoutStops() {

        List<ProviderRequestId> providerRequestIds = new ArrayList<>();
        providerRequestIds.add(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID));

        ItineraryCheckIn expectedItineraryCheckIn = CheckInTestObjectMother.oneWayWithoutStopsItineraryCheckIn();
        SegmentCheckIn expectedOutboundSegmentCheckIn = expectedItineraryCheckIn.segmentCheckIns().get(0);

        when(getCheckInOutboundPort.getProviderRequestIds(any())).thenReturn(providerRequestIds);
        when(getCheckInStatusOutboundPort.getStatus(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID)))
                .thenReturn(expectedOutboundSegmentCheckIn);

        ItineraryCheckIn itineraryCheckIn = getCheckInStatusUseCase.getStatus(ItineraryCheckInId.create());

        Section outboundSection = itineraryCheckIn.sections().get(0);
        Section expectedOutboundSection = expectedOutboundSegmentCheckIn.sections().get(0);

        assertEquals(itineraryCheckIn.sections().size(), 1);
        assertSame(outboundSection, expectedOutboundSection);
    }

    @Test
    public void oneWayWithStops() {

        List<ProviderRequestId> providerRequestIds = new ArrayList<>();
        providerRequestIds.add(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID));

        ItineraryCheckIn expectedItineraryCheckIn = CheckInTestObjectMother.oneWayWithStopsItineraryCheckIn();
        SegmentCheckIn expectedOutboundSegmentCheckIn = expectedItineraryCheckIn.segmentCheckIns().get(0);

        when(getCheckInOutboundPort.getProviderRequestIds(any())).thenReturn(providerRequestIds);
        when(getCheckInStatusOutboundPort.getStatus(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID)))
                .thenReturn(expectedOutboundSegmentCheckIn);

        ItineraryCheckIn itineraryCheckIn = getCheckInStatusUseCase.getStatus(ItineraryCheckInId.create());

        Section outboundSectionA = itineraryCheckIn.sections().get(0);
        Section outboundSectionB = itineraryCheckIn.sections().get(1);
        Section expectedOutboundSectionA = expectedOutboundSegmentCheckIn.sections().get(0);
        Section expectedOutboundSectionB = expectedOutboundSegmentCheckIn.sections().get(1);

        assertEquals(itineraryCheckIn.sections().size(), 2);
        assertSame(outboundSectionA, expectedOutboundSectionA);
        assertSame(outboundSectionB, expectedOutboundSectionB);
    }

    @Test
    public void roundTripWithoutStops() {

        List<ProviderRequestId> providerRequestIds = new ArrayList<>();
        providerRequestIds.add(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID));
        providerRequestIds.add(ProviderRequestId.from(CheckInTestObjectMother.INBOUND_PROVIDER_REQUEST_ID));

        ItineraryCheckIn expectedItineraryCheckIn = CheckInTestObjectMother.roundTripWithoutStopsItineraryCheckIn();
        SegmentCheckIn expectedOutboundSegmentCheckIn = expectedItineraryCheckIn.segmentCheckIns().get(0);
        SegmentCheckIn expectedInboundSegmentCheckIn = expectedItineraryCheckIn.segmentCheckIns().get(1);

        when(getCheckInOutboundPort.getProviderRequestIds(any())).thenReturn(providerRequestIds);
        when(getCheckInStatusOutboundPort.getStatus(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID)))
                .thenReturn(expectedOutboundSegmentCheckIn);
        when(getCheckInStatusOutboundPort.getStatus(ProviderRequestId.from(CheckInTestObjectMother.INBOUND_PROVIDER_REQUEST_ID)))
                .thenReturn(expectedInboundSegmentCheckIn);

        ItineraryCheckIn itineraryCheckIn = getCheckInStatusUseCase.getStatus(ItineraryCheckInId.create());

        Section outboundSection = itineraryCheckIn.sections().get(0);
        Section inboundSection = itineraryCheckIn.sections().get(1);
        Section expectedOutboundSection = expectedOutboundSegmentCheckIn.sections().get(0);
        Section expectedInboundSection = expectedInboundSegmentCheckIn.sections().get(0);

        assertEquals(itineraryCheckIn.sections().size(), 2);
        assertSame(outboundSection, expectedOutboundSection);
        assertSame(inboundSection, expectedInboundSection);
    }

    @Test
    public void roundTripWithStops() {

        List<ProviderRequestId> providerRequestIds = new ArrayList<>();
        providerRequestIds.add(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID));
        providerRequestIds.add(ProviderRequestId.from(CheckInTestObjectMother.INBOUND_PROVIDER_REQUEST_ID));

        ItineraryCheckIn expectedItineraryCheckIn = CheckInTestObjectMother.roundTripWithStopsItineraryCheckIn();
        SegmentCheckIn expectedOutboundSegmentCheckIn = expectedItineraryCheckIn.segmentCheckIns().get(0);
        SegmentCheckIn expectedInboundSegmentCheckIn = expectedItineraryCheckIn.segmentCheckIns().get(1);

        when(getCheckInOutboundPort.getProviderRequestIds(any())).thenReturn(providerRequestIds);
        when(getCheckInStatusOutboundPort.getStatus(ProviderRequestId.from(CheckInTestObjectMother.OUTBOUND_PROVIDER_REQUEST_ID)))
                .thenReturn(expectedOutboundSegmentCheckIn);
        when(getCheckInStatusOutboundPort.getStatus(ProviderRequestId.from(CheckInTestObjectMother.INBOUND_PROVIDER_REQUEST_ID)))
                .thenReturn(expectedInboundSegmentCheckIn);

        ItineraryCheckIn itineraryCheckIn = getCheckInStatusUseCase.getStatus(ItineraryCheckInId.create());

        Section outboundSectionA = itineraryCheckIn.sections().get(0);
        Section outboundSectionB = itineraryCheckIn.sections().get(1);
        Section inboundSectionA = itineraryCheckIn.sections().get(2);
        Section inboundSectionB = itineraryCheckIn.sections().get(3);
        Section expectedOutboundSectionA = expectedOutboundSegmentCheckIn.sections().get(0);
        Section expectedOutboundSectionB = expectedOutboundSegmentCheckIn.sections().get(1);
        Section expectedInboundSectionA = expectedInboundSegmentCheckIn.sections().get(0);
        Section expectedInboundSectionB = expectedInboundSegmentCheckIn.sections().get(1);

        assertEquals(itineraryCheckIn.sections().size(), 4);
        assertSame(outboundSectionA, expectedOutboundSectionA);
        assertSame(outboundSectionB, expectedOutboundSectionB);
        assertSame(inboundSectionA, expectedInboundSectionA);
        assertSame(inboundSectionB, expectedInboundSectionB);
    }

    @Test(expectedExceptions = ItineraryCheckInNotFoundException.class)
    public void noProviderRequestsFound() {
        when(getCheckInOutboundPort.getProviderRequestIds(any())).thenReturn(Collections.emptyList());

        getCheckInStatusUseCase.getStatus(ItineraryCheckInId.create());

    }

}
