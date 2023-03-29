package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.OutboundAdapterBindings;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.CheckInOracleOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.AirobotOutboundAdapter;
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
    private AirobotOutboundAdapter airobotOutboundAdapter;
    @Mock
    private CheckInOracleOutboundAdapter checkInOracleOutboundAdapter;
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

        Injector injector = Guice.createInjector(binder -> binder.bind(AirobotOutboundAdapter.class).toInstance(airobotOutboundAdapter), binder -> binder.bind(CheckInOracleOutboundAdapter.class).toInstance(checkInOracleOutboundAdapter), new OutboundAdapterBindings());

        assertSame(injector.getInstance(AirobotOutboundAdapter.class), airobotOutboundAdapter);
        assertSame(injector.getInstance(CheckInOracleOutboundAdapter.class), checkInOracleOutboundAdapter);
        assertSame(injector.getInstance(DataSource.class), dataSource);

    }

    public static class TestInitialContextFactory implements InitialContextFactory {
        @Override
        public Context getInitialContext(Hashtable<?, ?> environment) {
            return context;
        }
    }
}
