package com.globant.academy.requests;

import java.util.HashMap;
import java.util.Map;

import com.globant.academy.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class BaseRequest {
    
    protected Response requestGet(String endpoint, Map<String, ?> headers) {
        return RestAssured.given().contentType(Constants.VALUE_CONTENT_TYPE).headers(headers).when().get(endpoint);
    }
    
    protected Response requestPost(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .body(body)
                          .when()
                          .post(endpoint);
    }
    
    protected Response requestPut(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .body(body)
                          .when()
                          .put(endpoint);
    }
    
    protected Response requestDelete(String endpoint, Map<String, ?> headers){
        return RestAssured.given()
                .contentType(Constants.VALUE_CONTENT_TYPE)
                .headers(headers)
                .when()
                .delete(endpoint);
    }
    
    protected  Map<String, String> createBaseHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.CONTENT_TYPE, Constants.VALUE_CONTENT_TYPE);
        return headers;
    }
    
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
