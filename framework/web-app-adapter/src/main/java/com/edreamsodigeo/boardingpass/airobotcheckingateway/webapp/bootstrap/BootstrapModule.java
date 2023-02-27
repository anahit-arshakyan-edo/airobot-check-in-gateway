package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.bootstrap;

import com.google.inject.Singleton;
import com.odigeo.commons.rest.servlet.EdoServletModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import java.util.Collections;
import java.util.Map;

/**
 * Module used for the application startup. Add here (in order) any other servlet or filter the application may use
 */
class BootstrapModule extends EdoServletModule {

    BootstrapModule() {
        super();
    }

    /**
     * Every servlet the application may use will be configured here
     */
    @Override
    protected final void configureCustomServlets() {
        bind(HttpServletDispatcher.class).in(Singleton.class);
        final Map<String, String> serviceApplicationParam = buildServiceApplicationParam();
        serve("/*").with(HttpServletDispatcher.class, serviceApplicationParam);
    }

    private Map<String, String> buildServiceApplicationParam() {
        return Collections.singletonMap("javax.ws.rs.Application", "com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.bootstrap.ServiceApplication");
    }
}
