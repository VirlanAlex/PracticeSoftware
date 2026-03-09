package tests;

import org.testng.annotations.Test;
import services.ImageService;

public class ImageBETest {
    @Test
    public void testMethod(){
        ImageService imageService = new ImageService();
        imageService.obtainAllImages();
    }
}
