package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.*;
import org.testng.annotations.Test;
import services.UserService;
import sharedData.SharedData;
import types.ResponseStatusType;

@Feature("@FEATURE - USER")
@Story("@STORY - USER-321")
public class UserLoginBETest extends SharedData {

    @Test(description = "TC-111: User creation based on BE & login on FE")
    public void userTest() {

        // Pasul 1: Cream un nou user
        AddressModel addressModel = new AddressModel("Street 1", "City", "State", "Country", "1234AA");
        RequestUserModel requestBody = new RequestUserModel("Alex", "Doe", addressModel, "0987654321", "1970-01-01", "SuperSecure@123", "alex.text2@gmail.com");

        UserService userService = new UserService();
        ResponseUserModel responseBody = userService.createUser(requestBody);

        // Pasul 2: Ne logam cu userul creat
        ResponseUserLoginModel responseLoginBody = userService.loginUser(requestBody);

        // Pasul 3: Verificam ca s-a creat userul
        userService.checkUser(responseLoginBody.getAccess_token(), responseBody.getId(), ResponseStatusType.RESPONSE_OK);

        // Pasul 4: Delogam userul
        userService.logOutUser(responseLoginBody.getAccess_token());

        // Pasul 5: Logam user admin
        RequestUserLoginModel requestAdminBody = new RequestUserLoginModel ("admin@practicesoftwaretesting.com", "welcome01");
        ResponseUserLoginModel responseAdminBody = userService.loginUser(requestAdminBody);

        // Pasul 6: Stergem un user
        userService.deleteUser(responseAdminBody.getAccess_token(), responseBody.getId());

        // Pasul 7: Verificam ca userul s-a sters
        userService.checkUser(responseLoginBody.getAccess_token(), responseBody.getId(),ResponseStatusType.RESPONSE_UNAUTHORIZED);


    }
}
