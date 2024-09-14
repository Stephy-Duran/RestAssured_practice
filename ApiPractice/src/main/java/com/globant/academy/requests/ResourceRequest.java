package com.globant.academy.requests;


import com.globant.academy.models.Resource;
import com.globant.academy.utils.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Locale;

public class ResourceRequest extends BaseRequest {
    
    private String endpoint;
    
    /**
     *Gets Resources list
     *
     * @return the HTTP response containing the list of Resources
     */
    public Response getResources() {
        endpoint = String.format(Constants.URL, Constants.RESOURCE_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }
    
    /**
     *Creates a resource
     *
     *@param resource object to be created
     * @return the HTTP response from the POST request, which includes the created resource details
     */
    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.URL, Constants.RESOURCE_PATH);
        return requestPost(endpoint, createBaseHeaders(), resource);
    }
    
    /**
     * Updates an existing resource.
     *
     * @param resource the resource object containing updated details
     * @param resourceId the ID of the resource to be updated
     * @return the HTTP response from the PUT request, which includes the updated resource details
     */
    public Response updateResource(Resource resource, String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCE_PATH, resourceId);
        return requestPut(endpoint, createBaseHeaders(), resource);
    }
    
    /**
     * Deserializes the response's body to a list of `Resource` objects.
     *
     * @param response the HTTP response to be deserialized
     * @return a list of deserialized `Resource` objects from the response
     */
    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }
    
    /**
     * Creates a Resource object with fake data
     *
     * @return the HTTP response from the POST request,  which includes the created resource data
     */
    public Response createResourceWithFakeValues() {
        Faker fakeData = new Faker(new Locale("en"));
        Resource resource = Resource.builder()
                                    .name(fakeData.commerce().productName())
                                    .trademark(fakeData.company().name())
                                    .stock(fakeData.random().nextInt(1000))
                                    .price(fakeData.number().randomDouble(2, 1000, 1000000))
                                    .description(fakeData.lorem().paragraph())
                                    .tags("id")
                                    .active(fakeData.bool().bool())
                                    .build();
        return this.createResource(resource);
    }
}
