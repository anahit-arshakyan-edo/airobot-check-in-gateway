package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCaseImpl;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.google.inject.AbstractModule;

public class ApplicationModuleBindings extends AbstractModule {

    @Override
    protected void configure() {
        bind(GetAvailabilityUseCase.class).to(GetAvailabilityUseCaseImpl.class);
    }

}
