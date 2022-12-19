package com.edreamsodigeo.boardingpass.airobotcheckingateway.steps;

import com.google.inject.Inject;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.ServerConfiguration;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import static org.testng.Assert.assertEquals;


@ScenarioScoped
public class ServiceStatusSteps {

    private final ServerConfiguration serverConfiguration;
    private int statusCode;

    @Inject
    public ServiceStatusSteps(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }


    @When("^a call to the (.*) url is done$")
    public void callEngineeringCheckUrl(String resource) throws Throwable {
        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet("http://" + serverConfiguration.getServer() + ServerConfiguration.ENGINEERING_CONTEXT + resource))) {
            statusCode = response.getStatusLine().getStatusCode();
        }
    }

    @Then("^the response has status code (\\d+)$")
    public void checkHealthCheckResponseStatus(int expectedStatusCode) throws Throwable {
        assertEquals(statusCode, expectedStatusCode);
    }
}
