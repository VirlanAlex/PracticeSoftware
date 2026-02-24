package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
    // Trebuie sa fac doua actiuni pe aceasta clasa
    // Trebuie sa  configurez clientul
    // Pe baza configurarilor trebuie sa pot sa execut orice actiune (get,post, put, detele )

    public Response performRequest(String requestType, RequestSpecification request, String endpoint) {
        switch (requestType) {
            case "POST":
                prepareClient(request).post(endpoint);
                break;
            case "PUT":
                prepareClient(request).put(endpoint);
                break;
            case "GET":
                prepareClient(request).get(endpoint);
                break;
            case "DELETE":
                prepareClient(request).delete(endpoint);
                break;

    }
        return null;
    }

    public RequestSpecification prepareClient(RequestSpecification request){
        RestAssured.baseURI = "https://api.practicesoftwaretesting.com";
        request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.header("Accept", "application json");
        return request;
    }
}
