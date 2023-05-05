package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCase;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status.GetCheckInStatusUseCase;
import com.google.inject.AbstractModule;
import org.apache.log4j.Logger;

import javax.naming.NamingException;

public class ApplicationModuleBindings extends AbstractModule {

    private static final Logger LOGGER = Logger.getLogger(ApplicationModuleBindings.class);

    @Override
    protected void configure() {
        bindEJB(GetAvailabilityUseCase.class);
        bindEJB(CreateCheckInUseCase.class);
        bindEJB(GetCheckInStatusUseCase.class);
    }

    private <T> void bindEJB(Class<T> clazz) {
        try {
            bind(clazz).toProvider(JeeServiceProvider.getInstance(clazz));
        } catch (NamingException e) {
            LOGGER.error("Unable to create provider for class " + clazz, e);
        }
    }

}
