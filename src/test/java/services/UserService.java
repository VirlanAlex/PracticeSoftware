package services;

import client.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.*;
import org.testng.Assert;
import types.EndPointType;
import types.RequestMethodType;
import types.ResponseStatusType;
import utils.LogUtility;

public class UserService {

    // Aceasta clasa reprezinta metodele de la serviciul User de pe Swagger

    public ResponseUserModel createUser(RequestUserModel requestBody){
        LogUtility.infoLog("STEP 1: CREATE USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest(RequestMethodType.REQUEST_POST,request, EndPointType.USER_CREATE_ENDPOINT);
        LogUtility.infoLog(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), ResponseStatusType.RESPONSE_CREATED);
        return response.getBody().as(ResponseUserModel.class);
    }

    public ResponseUserLoginModel loginUser(RequestUserModel requestBody){
        LogUtility.infoLog("STEP 2: LOG IN USER REQUEST");
        RequestUserLoginModel requestLoginBody = new RequestUserLoginModel(requestBody.getEmail(), requestBody.getPassword());
        RequestSpecification request = RestAssured.given();
        request.body(requestLoginBody);

        Response response = performRequest(RequestMethodType.REQUEST_POST,request,EndPointType.USER_LOGIN_ENDPOINT);
        LogUtility.infoLog(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), ResponseStatusType.RESPONSE_OK);
        return response.getBody().as(ResponseUserLoginModel.class);
    }

    public void checkUser(String token, String userId, int statusCode){
        LogUtility.infoLog("STEP 3: CHECK USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ token);

        Response response3 = performRequest(RequestMethodType.REQUEST_GET,request,EndPointType.USER_SPECIFIC_ENDPOINT+ userId);
        LogUtility.infoLog(response3.getStatusLine());
        response3.body().prettyPrint();
        Assert.assertEquals(response3.getStatusCode(), statusCode);
    }

    public void logOutUser(String token){
        LogUtility.infoLog("STEP 4: LOGOUT USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ token);

        Response response4 = performRequest(RequestMethodType.REQUEST_GET,request,EndPointType.USER_LOGOUT_ENDPOINT);
        LogUtility.infoLog(response4.getStatusLine());
        response4.body().prettyPrint();
        Assert.assertEquals(response4.getStatusCode(), ResponseStatusType.RESPONSE_OK);
    }

    public ResponseUserLoginModel loginUser(RequestUserLoginModel requestBody){
        LogUtility.infoLog("STEP 2: LOG IN USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest(RequestMethodType.REQUEST_POST,request,EndPointType.USER_LOGIN_ENDPOINT);
        LogUtility.infoLog(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), ResponseStatusType.RESPONSE_OK);
        return response.getBody().as(ResponseUserLoginModel.class);

    }

    public void deleteUser(String token, String userId){
        LogUtility.infoLog("STEP 6: DELETE USER REQUEST");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ token);

        Response response6 = performRequest(RequestMethodType.REQUEST_DELETE,request,EndPointType.USER_SPECIFIC_ENDPOINT+ userId);
        LogUtility.infoLog(response6.getStatusLine());
        response6.body().prettyPrint();
        Assert.assertEquals(response6.getStatusCode(), ResponseStatusType.RESPONSE_NO_CONTENT);
    }

    private Response performRequest(String requestType, RequestSpecification request, String endpoint){
        return new RestClient().performRequest(requestType,request,endpoint);

    }
}
