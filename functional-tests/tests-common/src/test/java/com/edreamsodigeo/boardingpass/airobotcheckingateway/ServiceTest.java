package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreams.configuration.ConfigurationEngine;
import com.google.inject.Inject;
import com.odigeo.commons.test.docker.Artifact;
import com.odigeo.commons.test.docker.ContainerInfoBuilderFactory;
import com.odigeo.commons.test.docker.LogFilesTransfer;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.functionals.InstallerTestSuiteEnvironment;
import com.odigeo.technology.docker.ContainerComposer;
import com.odigeo.technology.docker.ContainerException;
import com.odigeo.technology.docker.ContainerInfo;
import com.odigeo.technology.docker.DockerModule;
import com.odigeo.technology.docker.ImageController;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;

@CucumberOptions(format = {"pretty", "json:target/cucumber/cucumber.json", "html:target/cucumber/"}, strict = true)
@Guice(modules = DockerModule.class)
public class ServiceTest extends AbstractTestNGCucumberTests {

    private static final Logger LOGGER = Logger.getLogger(ServiceTest.class);

    @Inject
    private ContainerComposer containerComposer;
    @Inject
    private LogFilesTransfer logFilesTransfer;
    @Inject
    private InstallerTestSuiteEnvironment installerTestSuiteEnvironment;
    @Inject
    private ContainerInfoBuilderFactory containerInfoBuilderFactory;
    @Inject
    private ImageController imageController;

    Artifact artifact = new Artifact(System.getProperty("module.groupId"), System.getProperty("module.name"), System.getProperty("module.version"));

    @BeforeClass
    public void setUp() throws Exception {
        ConfigurationEngine.init();
        try {
            ContainerInfo moduleContainer = containerInfoBuilderFactory.newModuleWithEmptyOracle(artifact).build();
            containerComposer.addServiceAndDependencies(moduleContainer, 100, 5000).composeUp(true);
        } catch (ContainerException e) {
            LOGGER.error("error", e);
            logFilesTransfer.copyToExternalFolder(artifact);
            throw e;
        } catch (IllegalArgumentException e) {
            LOGGER.error("error", e);
            throw e;
        }
        installerTestSuiteEnvironment.install();
    }

    @AfterClass
    public void tearDown() throws Exception {
        installerTestSuiteEnvironment.uninstall();
        containerComposer.composeDown(); // stop and remove the containers
        logFilesTransfer.copyToExternalFolder(artifact); //copy the logs from the standard host log folder to a configuracle folder
        imageController.removeFuncionalTestsImage(System.getProperty("module.name")); //
    }
}
