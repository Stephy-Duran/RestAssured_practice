package com.globant.academy.stepDefinitions;

import com.globant.academy.models.Resource;
import com.globant.academy.requests.ResourceRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResourceStepsDefinitions {
    
    private static final Logger log = LoggerFactory.getLogger(ResourceStepsDefinitions.class);
    private final ResourceRequest resourceRequest = new ResourceRequest();
    private Response response;
    private Resource resource;
    
    @Given("there are at least {int} registered clients in the system")
    public void createResources(int amountOfResources) {
        response = resourceRequest.getResources();
        List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
        while(resourceList.size() < amountOfResources) {
            response = resourceRequest.createResourceWithFakeValues();
            Assert.assertEquals(201, response.statusCode());
            //Update de list
            response = resourceRequest.getResources();
            resourceList = resourceRequest.getResourcesEntity(response);
        }
        log.info("There are {} or more resources in the system", amountOfResources);
    }
    
    @When("I send a GET request to find all active resources")
    public void retrieveTheDetailsOfTheAllActiveResources() {
        response = resourceRequest.getResources();
        Assert.assertEquals(200, response.statusCode());
        log.info("The resources were found");
    }
    
    @When("I send a PUT request to update all retrieved resources to inactive")
    public void sendAPUTRequestToUpdateAllRetrievedResourcesToInactive() {
        response = resourceRequest.getResources();
        List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
        for(Resource resourceI : resourceList) {
            if(resourceI.isActive()) {
                resourceI.setActive(false);
                Response updateResponse = resourceRequest.updateResource(resourceI, resourceI.getId());
                Assert.assertEquals(200, updateResponse.statusCode());
                log.info("Resource {} updated to inactive", resourceI.getName());
            }
        }
        log.info("All resources are in an inactive state.");
    }
    
    @When("I sent a GET request to find the latest resource")
    public void iSendAGETRequestToFindTheLatestResource() {
        response = resourceRequest.getResources();
        List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
        resource = resourceList.getLast();
        log.info("\nThe last resource was found: {}", resource.toString());
    }
    
    @When("I sent a PUT request to update all the parameters of this resource")
    public void iSentAPUTRequestToUpdateAllTheParametersOfThisResource() {
        Faker fakeData = new Faker();
        resource = resource.toBuilder()
                           .name(fakeData.commerce().productName())
                           .trademark(fakeData.company().name())
                           .stock(fakeData.random().nextInt(1000))
                           .price(fakeData.number().randomDouble(2, 1000, 1000000))
                           .description(fakeData.lorem().paragraph())
                           .tags("id")
                           .active(fakeData.bool().bool())
                           .build();
        response = resourceRequest.updateResource(resource, resource.getId());
        Assert.assertEquals(200, response.statusCode());
        log.info("\nThe resource was updated.{}", response.body().asPrettyString());
    }
    
    @Then("the response's body structure matches with resources list Json schema")
    public void doesResponseBodyStructureMatchResourcesListJsonSchema() {
        String resourcesPathSchema = "schemas/resourcesList.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, resourcesPathSchema));
        log.info("Successfully Validated schema from resource List objects");
    }
    
    @Then("the response should have a status code of {int}")
    public void theStatusCodeOfTheRequestWas(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }
    
    @Then("the response's body structure matches with resources Json schema")
    public void doesResponseBodyStructureMatchResourcesJsonSchema() {
        String resourceSchemaPath = "schemas/resources.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, resourceSchemaPath));
        log.info("Successfully Validated schema from resource object");
    }
}
