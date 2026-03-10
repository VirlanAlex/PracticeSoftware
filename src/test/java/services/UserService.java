package services;

import client.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.*;
import org.testng.Assert;
import types.EndPointType;
import types.RequestMethodType;

public class UserService {

    // Aceasta clasa reprezinta metodele de la serviciul User de pe Swagger

    public ResponseUserModel createUser(RequestUserModel requestBody){
        System.out.println("STEP 1: CREATE USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest(RequestMethodType.REQUEST_POST,request, EndPointType.USER_CREATE_ENDPOINT);
        System.out.println(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
        return response.getBody().as(ResponseUserModel.class);
    }

    public ResponseUserLoginModel loginUser(RequestUserModel requestBody){
        System.out.println("STEP 2: LOG IN USER REQUEST");
        RequestUserLoginModel requestLoginBody = new RequestUserLoginModel(requestBody.getEmail(), requestBody.getPassword());
        RequestSpecification request = RestAssured.given();
        request.body(requestLoginBody);

        Response response = performRequest(RequestMethodType.REQUEST_POST,request,EndPointType.USER_LOGIN_ENDPOINT);
        System.out.println(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        return response.getBody().as(ResponseUserLoginModel.class);
    }

    public void checkUser(String token, String userId, int statusCode){
        System.out.println("STEP 3: CHECK USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ token);

        Response response3 = performRequest(RequestMethodType.REQUEST_GET,request,EndPointType.USER_SPECIFIC_ENDPOINT+ userId);
        System.out.println(response3.getStatusLine());
        response3.body().prettyPrint();
        Assert.assertEquals(response3.getStatusCode(), statusCode);
    }

    public void logOutUser(String token){
        System.out.println("STEP 4: LOGOUT USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ token);

        Response response4 = performRequest(RequestMethodType.REQUEST_GET,request,EndPointType.USER_LOGOUT_ENDPOINT);
        System.out.println(response4.getStatusLine());
        response4.body().prettyPrint();
        Assert.assertEquals(response4.getStatusCode(), 200);
    }

    public ResponseUserLoginModel loginUser(RequestUserLoginModel requestBody){
        System.out.println("STEP 2: LOG IN USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest(RequestMethodType.REQUEST_POST,request,EndPointType.USER_LOGIN_ENDPOINT);
        System.out.println(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        return response.getBody().as(ResponseUserLoginModel.class);

    }

    public void deleteUser(String token, String userId){
        System.out.println("STEP 6: DELETE USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ token);

        Response response6 = performRequest(RequestMethodType.REQUEST_DELETE,request,EndPointType.USER_SPECIFIC_ENDPOINT+ userId);
        System.out.println(response6.getStatusLine());
        response6.body().prettyPrint();
        Assert.assertEquals(response6.getStatusCode(), 204);
    }

    private Response performRequest(String requestType, RequestSpecification request, String endpoint){
        return new RestClient().performRequest(requestType,request,endpoint);

    }
}
