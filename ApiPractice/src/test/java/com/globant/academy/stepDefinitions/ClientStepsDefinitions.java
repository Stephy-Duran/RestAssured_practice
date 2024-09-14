package com.globant.academy.stepDefinitions;

import com.globant.academy.models.Client;
import com.globant.academy.models.Resource;
import com.globant.academy.requests.ClientRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClientStepsDefinitions {
    
    private static final Logger log = LoggerFactory.getLogger(ClientStepsDefinitions.class);
    private final ClientRequest clientRequest = new ClientRequest();
    private Response response;
    private Client client;
    
    @Given("there are at least ten registered clients in the system")
    public void createClientsInTheSystem() {
        response = clientRequest.getClients();
        Assert.assertEquals(200, response.statusCode());
        List<Client> clientList = clientRequest.getClientsEntity(response);
        while(clientList.size() < 10) {
            response = clientRequest.createClientWithFakeData("fake");
            Assert.assertEquals(201, response.statusCode());
            response = clientRequest.getClients();
            clientList = clientRequest.getClientsEntity(response);
        }
        //Create 3 clients laura
        for(int i = 0; i < 3; i++) {
            response = clientRequest.createClientWithFakeData("laura");
            Assert.assertEquals(201, response.statusCode());
        }
        response = clientRequest.getClients();
        log.info(response.body().asPrettyString());
    }
    
    //@When("I send a DELETE request to delete all clients")
    public void deleteAllClients() {
        response = clientRequest.getClients();//obtengo todos lo clientes
        List<Client> clientList = clientRequest.getClientsEntity(response);  // deserializar la lista de clientes
        List<String> existingClientsIds = new ArrayList<>();
        for(Client client : clientList) {
            existingClientsIds.add(client.getId());
        }
        for(String id : existingClientsIds) {
            response = clientRequest.deleteClient(id);
        }
        response = clientRequest.getClients();
        clientList = clientRequest.getClientsEntity(response);
        Assert.assertTrue("Clients were not deleted correctly", clientList.isEmpty());
        log.info("All clients were removed");
    }
    
    @Then("The response to the request should have a status code of {int}")
    public void theStatusCodeOfTheRequestWas(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }
    
    @Then("the response's body structure matches with client Json schema")
    public void theResponseSBodyStructureIsCorrect() {
        String path = "schemas/clients.json";
        Assert.assertTrue(clientRequest.validateSchema(response, path));
        log.info("Successfully Validated schema from Client object");
    }
    
    //@Then("the client list should be empty")
    public void clientListShouldBeEmpty() {
        response = clientRequest.getClients();
        List<Client> clientList = clientRequest.getClientsEntity(response);
        Assert.assertTrue(clientList.isEmpty());
    }
    
    @When("I send a POST to create a new client")
    public void createANewClient() {
        response = clientRequest.createClientWithFakeData("fake");
        client = clientRequest.getClientEntity(response);
        log.info("New client: {}", response.body().asPrettyString());
        Assert.assertEquals(201, response.statusCode());
    }
    
    @And("I retrieve the details of the new client")
    public void findTheNewClient() {
        response = clientRequest.getClients();
        List<Client> clientList = clientRequest.getClientsEntity(response);
        for(Client clientI : clientList) {
            if(clientI.getId().equalsIgnoreCase(client.getId())) {
                client = clientI;
                break;
            }
        }
    }
    
    @And("I update any client's {string} with the following value {string}")
    public void updateAnyClientSWithTheFollowing(String attribute, String value) {
        boolean isAttributeUpdated = true;
        switch(attribute.toLowerCase()) {
            case "name":
                client.setName(value);
                break;
            case "lastname":
                client.setLastName(value);
                break;
            case "country":
                client.setCountry(value);
                break;
            case "city":
                client.setCity(value);
                break;
            case "email":
                client.setEmail(value);
                break;
            case "phone":
                client.setPhone(value);
                break;
            default:
                log.info("Attribute not recognized: {}", attribute);
                isAttributeUpdated = false;
                break;
        }
        if(isAttributeUpdated) {
            response = clientRequest.updateClient(client, client.getId());
            log.info(response.body().asPrettyString());
            Assert.assertEquals(200, response.statusCode());
            log.info("Client's {} updated successfully to: {}", attribute, value);
        }
    }
    
    @And("I delete the new client")
    public void deleteClient() {
        response = clientRequest.deleteClient(client.getId());
        Assert.assertEquals(200, response.statusCode());
        log.info("the client has been removed");
    }
    
    @When("the client's phone number is updated to {string} for the first client that matches by {string}")
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
    
    @Then("I validate that the client is not in the system.")
    public void iValidateThatTheClientIsNotInTheSystem() {
        boolean isClientPresent = false;
        response = clientRequest.getClients();
        List<Client> resourceList = clientRequest.getClientsEntity(response);
        for(Client resource : resourceList) {
            if(resource.getId().equals(this.client.getId())) {
                isClientPresent = true;
                break;
            }
        }
        Assert.assertFalse(isClientPresent);
    }
}
