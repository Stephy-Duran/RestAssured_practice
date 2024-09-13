package com.globant.academy.requests;


import com.globant.academy.models.Resource;
import com.globant.academy.utils.Constants;
import com.google.gson.Gson;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourceRequest extends BaseRequest {
    
    private String endpoint;
    
    public Response getResources() {
        endpoint = String.format(Constants.URL, Constants.RESOURCE_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }
    
    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCE_PATH, resourceId);
        return requestGet(endpoint, createBaseHeaders());
    }
    
    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.URL, Constants.RESOURCE_PATH);
        return requestPost(endpoint, createBaseHeaders(), resource);
    }
    
    public Response updateResource(Resource resource, Resource resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCE_PATH, resourceId);
        return requestPut(endpoint, createBaseHeaders(), resource);
    }
    
    public Response deleteResource(Resource resourceId){
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCE_PATH, resourceId);
        return requestDelete(endpoint, createBaseHeaders());
    }
    
    public Response getResourceEntity(@NotNull Response response){
        return response.as(Response.class);
    }
    
    public List<Resource> getResources(@NotNull Response response){
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("",Resource.class);
    }
    
    //Deserialización es el proceso de convertir datos en formato JSON en objetos de Java.
    public Resource getResourceEntity(String resourceJson) {
        Gson gson = new Gson();
        return gson.fromJson(resourceJson, Resource.class);
    }
    
}
