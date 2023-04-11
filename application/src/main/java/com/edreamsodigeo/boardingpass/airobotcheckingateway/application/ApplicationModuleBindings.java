package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCaseImpl;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCaseImpl;
import com.google.inject.AbstractModule;

public class ApplicationModuleBindings extends AbstractModule {

    @Override
    protected void configure() {
        bind(GetAvailabilityUseCase.class).to(GetAvailabilityUseCaseImpl.class);
        bind(CreateCheckInUseCase.class).to(CreateCheckInUseCaseImpl.class);
    }

}
