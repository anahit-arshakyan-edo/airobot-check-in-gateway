package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.bootstrap;

import com.edreams.configuration.ConfigurationEngine;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.CheckInController;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.DefaultExceptionMapper;
import com.odigeo.commons.rest.error.UnhandledExceptionMapper;

import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceApplication extends Application {

    private final Set<Object> singletons = new HashSet<Object>();

    public ServiceApplication() {
        super();
        singletons.addAll(buildRestControllersAndProviders());
    }

    private List<?> buildRestControllersAndProviders() {
        return Arrays.asList(
                ConfigurationEngine.getInstance(CheckInController.class),
                new DefaultExceptionMapper(),
                new UnhandledExceptionMapper(),
                new JacksonContextResolver());
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.unmodifiableSet(singletons);
    }
}
