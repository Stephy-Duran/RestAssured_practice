package com.globant.academy.stepDefinitions;

import com.globant.academy.models.Client;
import com.globant.academy.requests.ClientRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class MyStepsDefinitions {
    
    private static final Logger log = LoggerFactory.getLogger(MyStepsDefinitions.class);
    private final ClientRequest clientRequest = new ClientRequest();
    private Response response;
    private Client client;
    
    @Given("there are at least ten registered clients in the system")
    public void thereAreTenRegisteredClientsInTheSystem() {
        response = clientRequest.getClients();
        Assert.assertEquals(200, response.statusCode());
        List<Client> clientList = clientRequest.getClientsEntity(response);
        while(clientList.size() < 10) {
            response = clientRequest.createClientWithFakeData();
            Assert.assertEquals(201, response.statusCode());
            response = clientRequest.getClients();
            clientList = clientRequest.getClientsEntity(response);
        }
        log.info(clientRequest.getClients().jsonPath().prettify());
    }
    
    @When("^the client's phone number is updated to \"([^\"]*)\" for the first client that matches by \"([^\"]*)" +
          "\"$")
    public void updateClientPhoneNumberByName(String newPhoneNumber, String name) {
        response = clientRequest.getClients();
        List<Client> clientList = clientRequest.getClientsEntity(response);
        boolean theClientWasFound = false;
        for(Client client : clientList) {
            if(client.getName().equalsIgnoreCase(name)) {
                theClientWasFound = true;
                String oldPhoneNumber = client.getPhone();
                log.info("Old phone number: {}", oldPhoneNumber);
                client.setPhone(newPhoneNumber);
                response = clientRequest.updateClient(client, client.getId());
                Assert.assertEquals(200, response.statusCode());
                log.info("New phone number: {}", client.getPhone());
                log.info("Phone number updated successfully for client: {}", client.getName());
                Assert.assertFalse(oldPhoneNumber.equalsIgnoreCase(client.getPhone()));
                log.info(response.body().asPrettyString());
                break;
            }
        }
        if(!theClientWasFound) {
            log.info("No client found with the name: {} ", name);
        }
    }
    
    @When("I delete all clients")
    public void deleteAllClients() {
        //obtengo todos lo clientes
        response = clientRequest.getClients();
        // deserializar la lista de clientes
        List<Client> clientList = clientRequest.getClientsEntity(response);
        List<String> existingClientsId = new ArrayList<>();
        for(Client client : clientList) {
            existingClientsId.add(client.getId());
        }
        for(String id : existingClientsId) {
            response = clientRequest.deleteClient(id);
        }
        response = clientRequest.getClients();
        clientList = clientRequest.getClientsEntity(response);
        Assert.assertTrue("Clients were not deleted correctly", clientList.isEmpty());
    }
    
    @Then("the status code of the request was {int}")
    public void theStatusCodeOfTheRequestWas(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }
    
   @Then("the response's body structure matches with client Json schema")
    public void theResponseSBodyStructureIsCorrect() {
        String path = "schemas/clients.json";
        Assert.assertTrue(clientRequest.validateSchema(response, path));
        log.info("Successfully Validated schema from Client List object");
    }
    
    @Then("the client list should be empty")
    public void theClientListShouldBeEmpty() {
        response = clientRequest.getClients();
        List<Client> clientList = clientRequest.getClientsEntity(response);
        Assert.assertTrue(clientList.isEmpty());
    }
}
