package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.GetAvailabilityUseCaseImpl;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.CreateCheckInUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.CreateCheckInUseCaseImpl;
import com.google.inject.AbstractModule;

public class ApplicationModuleBindings extends AbstractModule {

    @Override
    protected void configure() {
        bind(GetAvailabilityUseCase.class).to(GetAvailabilityUseCaseImpl.class);
        bind(CreateCheckInUseCase.class).to(CreateCheckInUseCaseImpl.class);
    }

}
