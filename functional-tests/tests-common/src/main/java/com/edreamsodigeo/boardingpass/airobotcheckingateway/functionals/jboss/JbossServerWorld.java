package com.edreamsodigeo.boardingpass.airobotcheckingateway.functionals.jboss;

import cucumber.runtime.java.guice.ScenarioScoped;
import org.apache.log4j.Logger;

@ScenarioScoped
public class JbossServerWorld {

    private static final Logger LOGGER = Logger.getLogger(JbossServerWorld.class);

    public void installScenario() {
        LOGGER.info("Start install jboss scenario");
        // Put here the operations needed before start an scenario for example cleans caches etc
        LOGGER.info("End install jboss scenario");
    }
}
