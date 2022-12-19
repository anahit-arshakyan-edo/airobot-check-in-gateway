package com.edreamsodigeo.boardingpass.airobotcheckingateway.dependency;

import com.odigeo.commons.rest.ServiceNotificator;
import com.odigeo.commons.rest.guice.DefaultRestUtilsModule;

public class MyDependencyServiceModule extends DefaultRestUtilsModule<MyDependencyService> {

    public MyDependencyServiceModule(ServiceNotificator... listeners) {
        super(MyDependencyService.class, listeners);
    }
}
