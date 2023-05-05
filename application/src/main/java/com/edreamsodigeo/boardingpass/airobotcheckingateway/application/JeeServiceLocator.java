package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;


import org.jboss.resteasy.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class JeeServiceLocator {

    private static final String IMPL_SUFFIX = "Impl";

    private static final Logger LOGGER = Logger.getLogger(JeeServiceLocator.class);
    private static final JeeServiceLocator INSTANCE = new JeeServiceLocator();

    private InitialContext jndiContext;
    private String servicesContext;

    private JeeServiceLocator() {
        try {
            initContext();
        } catch (NamingException nex) {
            LOGGER.error("Unable to find initial JNDI context.", nex);
        }
    }

    public static JeeServiceLocator getInstance() {
        return INSTANCE;
    }

    private void initContext() throws NamingException {
        if (jndiContext == null) {
            jndiContext = new InitialContext();
            servicesContext = "java:global/" + getApplicationName() + "/application/";
        }
    }

    public String getApplicationName() {
        try {
            return (String) jndiContext.lookup("java:app/AppName");
        } catch (NamingException e) {
            LOGGER.error("Application name not found");
            return null;
        }
    }

    private Object getServiceByName(final String jndiName) throws NamingException {
        initContext();
        return jndiContext.lookup(jndiName);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(final Class<T> service) throws NamingException {
        final String jndiName = servicesContext + service.getSimpleName() + IMPL_SUFFIX;
        return (T) getServiceByName(jndiName);
    }
}
