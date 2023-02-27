package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.AirobotResource;
import com.odigeo.commons.rest.configuration.URLConfigurationLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AirobotHealthCheck extends HealthCheck {

    private final URL url;

    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public AirobotHealthCheck() {
        try {
            URI uri = new URLConfigurationLoader().getURLConfiguration(AirobotResource.class).getUri();
            url = new URL(uri.toString() + "/checkin/availability");
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Exception while building Airobot healthCheck", e);
        }
    }

    @Override
    protected Result check() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (successful(responseCode)) {
                return Result.healthy("Connected successfully to %s", url);
            }
            return Result.unhealthy("Error when connecting to %s - status: %d", url, responseCode);
        } catch (IOException e) {
            return Result.unhealthy("Error when connecting to %s - exception: %s", url, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean successful(int responseCode) {
        return responseCode == 403;
    }

}
