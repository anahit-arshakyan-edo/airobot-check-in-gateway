package com.edreamsodigeo.boardingpass.airobotcheckingateway.commons.test.server;

import com.odigeo.commons.test.bootstrap.ServerBuilder;

public class JaxRsServiceHttpServer<Service> extends TestHttpServer {
    private final String path;

    public JaxRsServiceHttpServer(String path, int port) {
        super(port);
        this.path = path;
    }

    public void startServerWithService(Service service) throws ServerStopException, InterruptedException {
        checkedStop();
        server = new ServerBuilder().port(port).listenInAllInterfaces(true).jaxRsApplication(service, path).build();
        server.start();
    }
}
