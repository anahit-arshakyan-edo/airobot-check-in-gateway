package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.AirobotOutboundPort;
import com.google.inject.AbstractModule;

public class OutboundAdapterBindings extends AbstractModule {

    @Override
    protected void configure() {
        bind(AirobotOutboundPort.class).to(AirobotOutboundAdapter.class);
    }

}
