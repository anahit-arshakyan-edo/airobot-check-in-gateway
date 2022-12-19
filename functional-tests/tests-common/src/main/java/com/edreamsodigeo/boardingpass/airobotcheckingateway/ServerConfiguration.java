package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.google.inject.Singleton;
import com.odigeo.technology.docker.ContainerException;
import com.odigeo.technology.docker.DockerHostRetriever;

@Singleton
public class ServerConfiguration {

    public static final String ENGINEERING_CONTEXT = "/airobot-check-in-gateway/engineering/";

    private final DockerHostRetriever dockerHostRetriever;


    public ServerConfiguration() {
        dockerHostRetriever = new DockerHostRetriever();
    }

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public String getServer() throws ContainerException {
        return dockerHostRetriever.getDockerHost() + ":8080";
    }
}
