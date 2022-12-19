package com.edreamsodigeo.boardingpass.airobotcheckingateway.commons.test.server;

import com.odigeo.commons.test.bootstrap.Server;
import org.apache.log4j.Logger;

public abstract class TestHttpServer {
    private static final Logger LOGGER = Logger.getLogger(JaxRsServiceHttpServer.class);
    private static final int MAX_NUM_OF_RETRIES = 6;
    protected Server server;
    protected final int port;

    public TestHttpServer(int port) {
        this.port = port;
    }

    public void checkedStop() throws ServerStopException, InterruptedException {
        if (server != null) {
            int doneRetries = 0;
            while (server.getLocalPort() == port && doneRetries < MAX_NUM_OF_RETRIES) {
                LOGGER.warn("Retrying the stop of the server with port " + port);
                server.stop();
                Thread.sleep(500);
                doneRetries++;
            }
            if (server.getLocalPort() == port) {
                throw new ServerStopException("The server with port " + port + " could not be stopped");
            }
        }
    }

    public void stopIfStarted() {
        if (server != null) {
            server.stop();
        }
    }
}
