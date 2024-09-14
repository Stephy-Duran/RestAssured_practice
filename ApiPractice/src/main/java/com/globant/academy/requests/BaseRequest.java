package com.globant.academy.requests;

import java.util.HashMap;
import java.util.Map;

import com.globant.academy.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class BaseRequest {
    
    /**
     * Sends a GET request using Rest-Assured and returns the response.
     *
     * @param endpoint the URL of the API endpoint
     * @param headers a map containing request headers
     * @return the HTTP response from the GET request
     */
    protected Response requestGet(String endpoint, Map<String, ?> headers) {
        return RestAssured.given().contentType(Constants.VALUE_CONTENT_TYPE).headers(headers).when().get(endpoint);
    }
    
    /**
     * Sends a POST request using Rest-Assured and returns the response.
     *
     * @param endpoint the URL of the API endpoint
     * @param headers a map containing request headers
     * @param body the request body to be sent with the POST request
     * @return the HTTP response from the POST request
     */
    protected Response requestPost(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .body(body)
                          .when()
                          .post(endpoint);
    }
    
    /**
     * Sends a PUT request using Rest-Assured and returns the response.
     *
     * @param endpoint the URL of the API endpoint
     * @param headers a map containing request headers
     * @param body the request body to be sent with the PUT request
     * @return the HTTP response from the PUT request
     */
    protected Response requestPut(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .body(body)
                          .when()
                          .put(endpoint);
    }
    
    /**
     * Sends a DELETE request using Rest-Assured and returns the response.
     *
     * @param endpoint the URL of the API endpoint
     * @param headers a map containing request headers
     * @return the HTTP response from the DELETE request
     */
    protected Response requestDelete(String endpoint, Map<String, ?> headers){
        return RestAssured.given()
                .contentType(Constants.VALUE_CONTENT_TYPE)
                .headers(headers)
                .when()
                .delete(endpoint);
    }
    
    /**
     * Creates a map of base headers for the requests.
     *
     * @return a map containing the base headers with content type
     */
    protected  Map<String, String> createBaseHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.CONTENT_TYPE, Constants.VALUE_CONTENT_TYPE);
        return headers;
    }
    
    /**
     * Validates the JSON schema of the response body against a schema file.
     *
     * @param response the HTTP response to be validated
     * @param schemaPath the classpath location of the JSON schema file
     * @return true if the response body matches the schema, false otherwise
     */
    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true;
        }
        catch(AssertionError e) {
            return false;
        }
    }
}
