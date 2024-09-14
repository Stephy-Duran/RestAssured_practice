package com.globant.academy.requests;

import com.globant.academy.models.Client;
import com.globant.academy.utils.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientRequest extends BaseRequest {
    
    private String endpoint;
    
    /**
     *Gets Client list
     *
     * @return the HTTP response containing the list of clients
     */
    public Response getClients() {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }
    
    
    /**
     *Creates a Client
     *
     *@param client object to be created
     * @return the HTTP response from the POST request, which includes the created client details
     */
    public Response createClient(Client client) {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestPost(endpoint, createBaseHeaders(), client);
    }
    
    /**
     * Updates an existing client.
     *
     * @param client the client object containing updated details
     * @param clientId the ID of the client to be updated
     * @return the HTTP response from the PUT request, which includes the updated client details
     */
    public Response updateClient(Client client, String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestPut(endpoint, createBaseHeaders(), client);
    }
    
    /**
     * Deletes a client identified by the given client ID.
     *
     * @param clientId the ID of the client to be deleted
     * @return the HTTP response from the DELETE request
     */
    public Response deleteClient(String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestDelete(endpoint, createBaseHeaders());
    }
    
    /**
     * Creates a client object with fake data
     *
     * @param condition according to the condition is assigned the client's name
     * @return the HTTP response from the POST request,  which includes the created client data
     */
    public Response createClientWithFakeData(String condition) {
        Faker fakeData = new Faker();
        Client client = Client.builder()
                              .name(condition.equals("fake") ? fakeData.name().firstName() : "laura")
                              .lastName(fakeData.name().lastName())
                              .country(fakeData.country().name())
                              .city(fakeData.address().city())
                              .email(fakeData.internet().emailAddress())
                              .phone(fakeData.phoneNumber().cellPhone())
                              .build();
        return this.createClient(client);
    }
    
    /**
     * Deserializes the response's body to a Client object.
     
     *
     * @param response the HTTP response to be deserialized
     * @return the deserialized `Client` object from the response
     */
    public Client getClientEntity(@NotNull Response response) {
        return response.as(Client.class);
    }
    
    /**
     * Deserializes the response's body to a list of `Client` objects.
     
     *
     * @param response the HTTP response to be deserialized
     * @return a list of deserialized `Client` objects from the response
     */
    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }
}
