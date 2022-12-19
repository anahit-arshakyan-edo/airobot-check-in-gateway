package com.edreamsodigeo.boardingpass.airobotcheckingateway.functionals;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class InstallerTestSuiteEnvironment {

    private static final Logger logger = Logger.getLogger(InstallerTestSuiteEnvironment.class);

    public void install() throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        logger.info("Start install test suite environment");
        logger.info("End install environment");
    }

    public void uninstall() {
        logger.info("Start uninstall test suite environment");
        logger.info("End uninstall environment");
    }

}
