package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.bootstrap;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.edreams.configuration.ConfigurationEngine;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.ApplicationModuleBindings;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.OutboundAdapterBindings;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.healthcheck.AirobotHealthCheck;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResourceModule;
import com.odigeo.commons.config.files.ConfigurationFilesManager;
import com.odigeo.commons.monitoring.healthcheck.DataSourceHealthCheck;
import com.odigeo.commons.monitoring.healthcheck.HealthCheckServlet;
import com.odigeo.commons.monitoring.metrics.MetricsManager;
import com.odigeo.commons.monitoring.metrics.reporter.ReporterStatus;
import com.odigeo.commons.rest.bootstrap.EdoContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VFSUtils;
import org.jboss.vfs.VirtualFile;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.IOException;
import java.net.URL;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration.OutboundAdapterBindings.DEFAULT_DATASOURCE_NAME;

public class ContextListener extends EdoContextListener {

    private static final String LOG4J_FILENAME = "/log4j.properties";
    private static final long LOG4J_WATCH_DELAY_MS = 1800000L;

    @Override
    protected void startUpApplication(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();

        servletContext.log("Bootstrapping .....");
        Logger logger = initLog4J(servletContext);

        initHealthCheckRegistry(servletContext);

        initConfigurationEngine();

        initMetrics();

        logger.info("Bootstrapping finished!");
    }

    @Override
    protected void endApplication(ServletContextEvent event) {
        event.getServletContext().log("context destroyed");
    }

    private static String getAppName() {
        try {
            return InitialContext.doLookup("java:app/AppName");
        } catch (NamingException e) {
            throw new IllegalStateException("cannot get AppName", e);
        }
    }

    private void initConfigurationEngine() {
        // Add any other dependencies' modules here
        ConfigurationEngine.init(
                new BootstrapModule(),
                new ApplicationModuleBindings(),
                new OutboundAdapterBindings(),
                new AirobotResourceModule.Builder().build());
    }

    private void initHealthCheckRegistry(ServletContext servletContext) {
        final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
        healthCheckRegistry.register("Airobot", new AirobotHealthCheck());
        healthCheckRegistry.register("OracleDB", new DataSourceHealthCheck(DEFAULT_DATASOURCE_NAME));

        servletContext.setAttribute(HealthCheckServlet.REGISTRY_KEY, healthCheckRegistry);
    }

    private Logger initLog4J(ServletContext servletContext) {
        VirtualFile virtualFile = VFS.getChild(new ConfigurationFilesManager().getConfigurationFileUrl(LOG4J_FILENAME, this.getClass()).getFile());
        URL fileRealURL;
        try {
            fileRealURL = VFSUtils.getPhysicalURL(virtualFile);
            PropertyConfigurator.configureAndWatch(fileRealURL.getFile(), LOG4J_WATCH_DELAY_MS);
            servletContext.log("Log4j has been initialized with config file " + fileRealURL.getFile() + " and watch delay of " + (LOG4J_WATCH_DELAY_MS / 1000) + " seconds");
        } catch (IOException e) {
            throw new IllegalStateException("Log4j cannot be initialized: file " + LOG4J_FILENAME + " cannot be load", e);
        }

        return Logger.getLogger(ContextListener.class);
    }

    private void initMetrics() {
        MetricsManager.getInstance().addMetricsReporter(getAppName());
        MetricsManager.getInstance().changeReporterStatus(ReporterStatus.STARTED);
    }
}
