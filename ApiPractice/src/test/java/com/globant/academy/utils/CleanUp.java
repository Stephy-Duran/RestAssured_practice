package com.globant.academy.utils;

import com.globant.academy.models.Client;
import com.globant.academy.requests.ClientRequest;
import com.globant.academy.stepDefinitions.ClientStepsDefinitions;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CleanUp {
    
    private static final Logger log = LoggerFactory.getLogger(CleanUp.class);
    private final ClientRequest clientRequest = new ClientRequest();
    Response response;
    
    public void clientListShouldBeEmpty() {
        response = clientRequest.getClients();
        List<Client> clientList = clientRequest.getClientsEntity(response);
        Assert.assertTrue(clientList.isEmpty());
    }
    
    public void deleteAllClients() {
        response = clientRequest.getClients();
        List<Client> clientList = clientRequest.getClientsEntity(response);
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
}
