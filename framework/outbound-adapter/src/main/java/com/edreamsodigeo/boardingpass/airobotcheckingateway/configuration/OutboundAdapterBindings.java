package com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.GenerateReferenceIdOracleOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.DeleteCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GenerateReferenceIdOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInStatusOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.GetCheckInOracleOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.SaveCheckInOracleOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.availability.GetAvailabilityAirobotApiOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.delete.DeleteCheckInAirobotOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.status.GetCheckInStatusAirobotApiOutboundAdapter;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.provider.create.RequestCheckInAirobotApiOutboundAdapter;
import com.google.inject.AbstractModule;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OutboundAdapterBindings extends AbstractModule {

    public static final String DEFAULT_DATASOURCE_NAME = "java:/jdbc/AirobotCheckInGatewayDataSource";

    @Override
    protected void configure() {
        bind(GetAvailabilityOutboundPort.class).to(GetAvailabilityAirobotApiOutboundAdapter.class);
        bind(RequestCheckInOutboundPort.class).to(RequestCheckInAirobotApiOutboundAdapter.class);
        bind(SaveCheckInOutboundPort.class).to(SaveCheckInOracleOutboundAdapter.class);
        bind(GetCheckInOutboundPort.class).to(GetCheckInOracleOutboundAdapter.class);
        bind(GetCheckInStatusOutboundPort.class).to(GetCheckInStatusAirobotApiOutboundAdapter.class);
        bind(GenerateReferenceIdOutboundPort.class).to(GenerateReferenceIdOracleOutboundAdapter.class);
        bind(DeleteCheckInOutboundPort.class).to(DeleteCheckInAirobotOutboundAdapter.class);
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
