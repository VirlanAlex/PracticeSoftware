package tests;

import models.AddressModel;
import models.RequestUserModel;
import models.ResponseUserModel;
import org.testng.annotations.Test;
import pages.LoginPage;
import services.UserService;
import sharedData.SharedData;

public class UserBETest extends SharedData {

    @Test
    public void userTest() {
        AddressModel addressModel = new AddressModel("Street 1", "City", "State", "Country", "1234AA");
        RequestUserModel requestBody = new RequestUserModel("Alex", "Doe", addressModel, "0987654321", "1970-01-01", "SuperSecure@123", "alex.text2@gmail.com");

        UserService userService = new UserService();
        ResponseUserModel responseBody = userService.createUser(requestBody);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginProcess(requestBody.getEmail(), requestBody.getPassword());
    }
}
