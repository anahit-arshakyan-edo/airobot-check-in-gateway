package com.edreamsodigeo.boardingpass.airobotcheckingateway.functionals;

import com.google.inject.Inject;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.commons.test.server.ServerStopException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.functionals.jboss.JbossServerWorld;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

@ScenarioScoped
public class InstallerScenarioEnvironment {

    private static final Logger LOGGER = Logger.getLogger(InstallerScenarioEnvironment.class);

    private final JbossServerWorld jbossServerWorld;

    @Inject
    public InstallerScenarioEnvironment(JbossServerWorld jbossServerWorld) {
        this.jbossServerWorld = jbossServerWorld;
    }

    @Before
    public void install() throws InterruptedException, SQLException, ClassNotFoundException, IOException, ServerStopException {
        LOGGER.info("Start install environment for a scenario");
        jbossServerWorld.installScenario();
        LOGGER.info("End install environment for a scenario");
    }

    @After
    public void uninstall() {
        LOGGER.info("Start uninstall environment for a scenario");
        LOGGER.info("End uninstall environment for a scenario");
    }

}
