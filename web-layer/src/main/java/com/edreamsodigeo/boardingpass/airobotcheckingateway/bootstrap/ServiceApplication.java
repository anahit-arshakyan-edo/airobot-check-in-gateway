package com.edreamsodigeo.boardingpass.airobotcheckingateway.bootstrap;

import com.edreams.configuration.ConfigurationEngine;
import com.odigeo.commons.rest.error.UnhandledExceptionMapper;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.service.MyServiceController;

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
        // TODO Add here every contract implementation (aka controller) and every provider (interceptors, exception mappers, etc) the application uses
        return Arrays.asList(ConfigurationEngine.getInstance(MyServiceController.class),
                new UnhandledExceptionMapper());
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.unmodifiableSet(singletons);
    }
}
