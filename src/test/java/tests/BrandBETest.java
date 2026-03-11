package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.*;
import org.testng.annotations.Test;
import services.BrandService;
import services.UserService;
import types.ResponseStatusType;

@Feature("@FEATURE - BRAND")
@Story("@STORY - BRAND")
public class BrandBETest {
    @Test
    public void brandTest() {
        // Pasul 1: Cream un Brand
        RequestBrandModel requestBody = new RequestBrandModel("Brand", "Testing1");

        BrandService brandService = new BrandService();
        ResponseBrandModel responseBody = brandService.createBrand(requestBody);

        // Pasul 2: Verificam ca s-a creat brandul
        brandService.checkSpecificBrand(responseBody.getId(), ResponseStatusType.RESPONSE_OK);

        // Pasul 3: Modificam un brand
        RequestBrandModel requestBody3= new RequestBrandModel("Alex", "TestNG");
        brandService.modifySpecificBrand(requestBody3, responseBody.getId());

         // Pasul 4: Verificam ca s-a modificat brandul
        brandService.checkSpecificBrand(responseBody.getId(),ResponseStatusType.RESPONSE_OK);

        // Pasul 5: Ne logam cu ADMIN creat
        UserService userService = new UserService();
        RequestUserLoginModel requestAdminBody = new RequestUserLoginModel ("admin@practicesoftwaretesting.com", "welcome01");
        ResponseUserLoginModel responseAdminBody = userService.loginUser(requestAdminBody);

        // Pasul 6: Stergem brandul
        brandService.deleteSpecificBrand(responseAdminBody.getAccess_token(),responseBody.getId());

        // Pasul 7: Verificam ca brandul s-a sters
        brandService.checkSpecificBrand(responseBody.getId(),ResponseStatusType.RESPONSE_NOT_FOUND);


    }
}
