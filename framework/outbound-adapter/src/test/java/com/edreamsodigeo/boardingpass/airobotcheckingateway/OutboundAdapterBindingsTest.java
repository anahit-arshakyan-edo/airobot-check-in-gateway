package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.OutboundAdapterBindings;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.SaveCheckInOracleOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.GetAvailabilityAirobotOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.RequestCheckInAirobotOutboundAdapter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;

import java.util.Hashtable;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.OutboundAdapterBindings.DEFAULT_DATASOURCE_NAME;
import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertSame;

public class OutboundAdapterBindingsTest {

    @Mock
    private GetAvailabilityAirobotOutboundAdapter airobotOutboundAdapter;
    @Mock
    private RequestCheckInAirobotOutboundAdapter requestCheckInAirobotOutboundAdapter;
    @Mock
    private SaveCheckInOracleOutboundAdapter saveCheckInOracleOutboundAdapter;
    @Mock
    private static Context context;
    @Mock
    private DataSource dataSource;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
        System.setProperty(INITIAL_CONTEXT_FACTORY, TestInitialContextFactory.class.getName());
    }

    @Test
    public void testBindings() throws NamingException {
        when(context.lookup(DEFAULT_DATASOURCE_NAME)).thenReturn(dataSource);

        Injector injector = Guice.createInjector(binder -> binder.bind(GetAvailabilityAirobotOutboundAdapter.class).toInstance(airobotOutboundAdapter), binder -> binder.bind(RequestCheckInAirobotOutboundAdapter.class).toInstance(requestCheckInAirobotOutboundAdapter), binder -> binder.bind(SaveCheckInOracleOutboundAdapter.class).toInstance(saveCheckInOracleOutboundAdapter), new OutboundAdapterBindings());

        assertSame(injector.getInstance(GetAvailabilityAirobotOutboundAdapter.class), airobotOutboundAdapter);
        assertSame(injector.getInstance(SaveCheckInOracleOutboundAdapter.class), saveCheckInOracleOutboundAdapter);
        assertSame(injector.getInstance(RequestCheckInAirobotOutboundAdapter.class), requestCheckInAirobotOutboundAdapter);
        assertSame(injector.getInstance(DataSource.class), dataSource);

    }

    public static class TestInitialContextFactory implements InitialContextFactory {
        @Override
        public Context getInitialContext(Hashtable<?, ?> environment) {
            return context;
        }
    }
}
