package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BrandBETest {
    @Test
    public void brandTest() {
        RestAssured.baseURI = "https://api.practicesoftwaretesting.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.header("Accept", "application json");

        // Pasul 1: Cream un Brand
        System.out.println("STEP 1: CREATE NEW BRAND");

        RequestBrandModel requestBody = new RequestBrandModel("Brand", "Testing1");

        request.body(requestBody);
        Response response = request.post("/brands");
        System.out.println(response.getStatusLine());
        response.body().prettyPrint();
        ResponseBrandModel responseBody = response.getBody().as(ResponseBrandModel.class);
        Assert.assertEquals(response.getStatusCode(), 201);

        // Pasul 2: Verificam ca s-a creat brandul
        System.out.println("STEP 2: CHECK BRAND REQUEST");

        Response response2 = request.get("/brands/"+ responseBody.getId());
        System.out.println(response2.getStatusLine());
        response2.body().prettyPrint();
        Assert.assertEquals(response2.getStatusCode(), 200);
    }
}
