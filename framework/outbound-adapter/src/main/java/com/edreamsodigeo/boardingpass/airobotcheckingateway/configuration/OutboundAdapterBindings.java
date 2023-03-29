package com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.AirobotOutboundAdapter;
import com.google.inject.AbstractModule;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OutboundAdapterBindings extends AbstractModule {

    public static final String DEFAULT_DATASOURCE_NAME = "java:/jdbc/AirobotCheckInGatewayDataSource";

    @Override
    protected void configure() {
        bind(AirobotOutboundPort.class).to(AirobotOutboundAdapter.class);
        bind(DataSource.class).toInstance(dataSourceInstance());
    }

    private DataSource dataSourceInstance() {
        try {
            return (DataSource) new InitialContext().lookup(DEFAULT_DATASOURCE_NAME);
        } catch (NamingException e) {
            throw new InvalidConfigurationException("Unavailable DataSource: " + DEFAULT_DATASOURCE_NAME, e);
        }
    }

}