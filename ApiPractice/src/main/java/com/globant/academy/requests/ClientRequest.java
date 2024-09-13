package com.globant.academy.requests;

import com.globant.academy.models.Client;
import com.globant.academy.utils.Constants;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientRequest extends BaseRequest {
    
    private String endpoint;
    
    public Response getClients() {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }
    
    public Response getClient(String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestGet(endpoint, createBaseHeaders());
    }
    
    public Response createClient(Client client) {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestPost(endpoint, createBaseHeaders(), client);
    }
    
    public Response updateClient(Client client, String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestPut(endpoint, createBaseHeaders(), client);
    }
    
    public Response deleteClient(String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestDelete(endpoint, createBaseHeaders());
    }
    
    public Response createClientWithFakeData() {
        Faker fakeData = new Faker();
        Client client = Client.builder()
                              .name(fakeData.name().firstName())
                              .lastName(fakeData.name().lastName())
                              .country(fakeData.country().name())
                              .city(fakeData.address().city())
                              .email(fakeData.internet().emailAddress())
                              .phone(fakeData.phoneNumber().cellPhone())
                              .build();
        return this.createClient(client);
    }
    
    public Response createClientLaura() {
        Faker fakeData = new Faker();
        Client client = Client.builder()
                              .name("Laura")
                              .lastName(fakeData.name().lastName())
                              .country(fakeData.country().name())
                              .city(fakeData.address().city())
                              .email(fakeData.internet().emailAddress())
                              .phone(fakeData.phoneNumber().cellPhone())
                              .build();
        return this.createClient(client);
    }
    
    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }
    
    public Client getClientEntity(String clientJson) {
        Gson gson = new Gson();
        return gson.fromJson(clientJson, Client.class);
    }
    
}
