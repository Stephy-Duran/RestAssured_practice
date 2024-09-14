package com.globant.academy.stepDefinitions;

import com.globant.academy.utils.Constants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Hooks {
    
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);
    
    @Before
    public void testStart(Scenario scenario) {
        log.info("*****************************************************************************************");
        log.info("	Scenario: " + scenario.getName());
        log.info("*****************************************************************************************");
        RestAssured.baseURI = Constants.BASE_URL;
    }
    
    @After
    public void cleanUp(Scenario scenario) {
        log.info("*****************************************************************************************");
        log.info("	Scenario finished: " + scenario.getName());
        log.info("*****************************************************************************************");
        
        if(scenario.getSourceTagNames().contains("@PostConditionDeleteClients")){
            try {
                ClientStepsDefinitions clientStepsDefinitions = new ClientStepsDefinitions();
                clientStepsDefinitions.deleteAllClients();
                clientStepsDefinitions.clientListShouldBeEmpty();
            } catch (Exception e) {
                log.error("Error during cleanup: " + e.getMessage());
            }
        }
    }
}
