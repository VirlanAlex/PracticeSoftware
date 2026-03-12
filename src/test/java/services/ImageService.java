package services;

import client.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import types.EndPointType;
import types.RequestMethodType;
import types.ResponseStatusType;
import utils.LogUtility;

public class ImageService extends CommonService {
    public void obtainAllImages(){
        LogUtility.infoLog("STEP 1: GET ALL IMAGES");
        RequestSpecification request = RestAssured.given();

        Response response = performRequest(RequestMethodType.REQUEST_GET, request, EndPointType.IMAGE_GET_ALL_ENDPOINT);
        LogUtility.infoLog(response.getStatusLine());
        LogUtility.infoLog(response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), ResponseStatusType.RESPONSE_OK);
    }
}
