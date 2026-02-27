package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import services.ImageService;

public class ImageBETest {
    @Test
    public void testMethod(){
        ImageService imageService = new ImageService();
        imageService.obtainAllImages();
    }
}
