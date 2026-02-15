package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.AddressModel;
import models.RequestUserModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import sharedData.SharedData;

public class UserBETest extends SharedData {

    @Test
    public void userTest() {
        RestAssured.baseURI = "https://api.practicesoftwaretesting.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.header("Accept", "application json");

        AddressModel addressModel = new AddressModel("Street 1", "City", "State", "Country", "1234AA");
        RequestUserModel requestBody = new RequestUserModel("Alex", "Doe", addressModel, "0987654321", "1970-01-01", "SuperSecure@123", "alex.text2@gmail.com");

        request.body(requestBody);
        Response response = request.post("/users/register");
        System.out.println(response.getStatusLine());
        response.body().prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginProcess(requestBody.getEmail(), requestBody.getPassword());
    }
}
