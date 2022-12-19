package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.google.inject.Guice;
import com.google.inject.Stage;
import com.odigeo.commons.test.guice.RandomModule;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;

public class Injector implements InjectorSource {
    @Override
    public com.google.inject.Injector getInjector() {
        return Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, new RandomModule());
    }
}
