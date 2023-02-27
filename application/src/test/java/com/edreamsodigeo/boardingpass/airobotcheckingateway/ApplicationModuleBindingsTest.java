package com.edreamsodigeo.boardingpass.airobotcheckingateway;


import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.ApplicationModuleBindings;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityInboundPort;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertSame;


public class ApplicationModuleBindingsTest {

    @Mock
    private AvailabilityInboundPort inboundPort;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testBindings() {
        Injector injector = Guice.createInjector(binder -> binder.bind(AvailabilityInboundPort.class).toInstance(inboundPort), new ApplicationModuleBindings());

        assertSame(injector.getInstance(AvailabilityInboundPort.class), inboundPort);

    }
}
