package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;

public class UserLoginBETest extends SharedData {

    @Test
    public void userTest() {
        RestAssured.baseURI = "https://api.practicesoftwaretesting.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.header("Accept", "application json");

        // Pasul 1: Cream un nou user
        System.out.println("STEP 1: CREATE USER REQUEST");
        AddressModel addressModel = new AddressModel("Street 1", "City", "State", "Country", "1234AA");
        RequestUserModel requestBody = new RequestUserModel("Alex", "Doe", addressModel, "0987654321", "1970-01-01", "SuperSecure@123", "alex.text2@gmail.com");

        request.body(requestBody);
        Response response = request.post("/users/register");
        System.out.println(response.getStatusLine());
        response.body().prettyPrint();
        ResponseUserModel responseBody = response.getBody().as(ResponseUserModel.class);
        Assert.assertEquals(response.getStatusCode(), 201);

        // Pasul 2: Ne logam cu userul creat
        System.out.println("STEP 2: LOG IN USER REQUEST");
        RequestUserLoginModel requestBody2 = new RequestUserLoginModel(requestBody.getEmail(), requestBody.getPassword());
        request.body(requestBody2);
        Response response2 = request.post("/users/login");
        System.out.println(response2.getStatusLine());
        response2.body().prettyPrint();
        ResponseUserLoginModel responseBody2 = response2.getBody().as(ResponseUserLoginModel.class);
        Assert.assertEquals(response2.getStatusCode(), 200);

        // Pasul 3: Verificam ca s-a creat userul
        System.out.println("STEP 3: CHECK USER REQUEST");
        request.header("Authorization", "Bearer "+ responseBody2.getAccess_token());
        Response response3 = request.get("/users/"+ responseBody.getId());
        System.out.println(response3.getStatusLine());
        response3.body().prettyPrint();
        Assert.assertEquals(response3.getStatusCode(), 200);

        // Pasul 4: Delogam userul
        System.out.println("STEP 4: LOGOUT USER REQUEST");
        request.header("Authorization", "Bearer "+ responseBody2.getAccess_token());
        Response response4 = request.get("/users/logout");
        System.out.println(response4.getStatusLine());
        response4.body().prettyPrint();
        Assert.assertEquals(response4.getStatusCode(), 200);

        // Pasul 5: Logam user admin
        System.out.println("STEP 5: LOGIN USER ADMIN REQUEST");
        RequestUserLoginModel requestBody5 = new RequestUserLoginModel ("admin@practicesoftwaretesting.com", "welcome01");
        request.body(requestBody5);
        Response response5 = request.post("/users/login");
        System.out.println(response5.getStatusLine());
        response5.body().prettyPrint();
        ResponseUserLoginModel responseBody5 = response5.getBody().as(ResponseUserLoginModel.class);
        Assert.assertEquals(response5.getStatusCode(), 200);

        // Pasul 6: Stergem un user
        System.out.println("STEP 6: DELETE USER REQUEST");
        request.header("Authorization", "Bearer "+ responseBody5.getAccess_token());
        Response response6 = request.delete("/users/"+ responseBody.getId());
        System.out.println(response6.getStatusLine());
        response3.body().prettyPrint();
        Assert.assertEquals(response6.getStatusCode(), 204);

        // Pasul 7: Verificam ca userul s-a sters
        System.out.println("STEP 7: CHECK USER REQUEST");
        request.header("Authorization", "Bearer "+ responseBody5.getAccess_token());
        Response response7 = request.get("/users/"+ responseBody.getId());
        System.out.println(response7.getStatusLine());
        response7.body().prettyPrint();
        Assert.assertEquals(response7.getStatusCode(), 404);


    }
}
