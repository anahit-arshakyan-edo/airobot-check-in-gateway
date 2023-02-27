package com.edreamsodigeo.boardingpass.airobotcheckingateway;


import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertSame;

public class OutboundAdapterBindingsTest {

    @Mock
    private AirobotOutboundAdapter outboundAdapter;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testBindings() {
        Injector injector = Guice.createInjector(binder -> binder.bind(AirobotOutboundAdapter.class).toInstance(outboundAdapter), new OutboundAdapterBindings());

        assertSame(injector.getInstance(AirobotOutboundAdapter.class), outboundAdapter);

    }
}
