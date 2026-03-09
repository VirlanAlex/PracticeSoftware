package client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

    public Response performRequest(String requestType, RequestSpecification request, String endpoint) {
        switch (requestType) {
            case "POST":
                return prepareClient(request).post(endpoint);
            case "PUT":
                return prepareClient(request).put(endpoint);
            case "GET":
                return prepareClient(request).get(endpoint);
            case "DELETE":
                return prepareClient(request).delete(endpoint);

        }

        return null;
    }

    private RequestSpecification prepareClient(RequestSpecification request){
        request.baseUri("https://api.practicesoftwaretesting.com");
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        return request;
    }
}