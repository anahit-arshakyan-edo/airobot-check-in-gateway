package com.edreamsodigeo.boardingpass.airobotcheckingateway.steps;

import cucumber.api.java.en.Given;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class GeneralSteps {

    @Given("^wait for (\\d+) seconds$")
    public void createProviderLocationDictionaryElements(Integer seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }
}
