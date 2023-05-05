package com.edreamsodigeo.boardingpass.airobotcheckingateway.application;

import com.edreams.base.BaseRuntimeException;
import com.google.inject.Provider;

import javax.naming.NamingException;

@SuppressWarnings("PMD")
public final class JeeServiceProvider<T> implements Provider<T> {

    private final Class<T> type;

    private JeeServiceProvider(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get() {
        try {
            return JeeServiceLocator.getInstance().getService(type);
        } catch (NamingException e) {
            throw new BaseRuntimeException("Unable to obtain an instance of service " + type, e);
        }
    }

    private static <T> void checkThatServiceExists(Class<T> type) throws NamingException {
        JeeServiceLocator.getInstance().getService(type);
    }

    public static <T> JeeServiceProvider<T> getInstance(Class<T> type) throws NamingException {
        checkThatServiceExists(type);
        return new JeeServiceProvider<>(type);
    }

}
