package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityInboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityUseCase;
import com.google.inject.AbstractModule;

public class ApplicationModuleBindings extends AbstractModule {

    @Override
    protected void configure() {
        bind(AvailabilityUseCase.class).to(AvailabilityInboundPort.class);
    }

}
