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

public class ReportService {

    public void generateAvarageSalesPerMonthReport(String token){
        LogUtility.infoLog("STEP 2: GENERATE REPORT");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer" + token);

        Response response2 = performRequest(RequestMethodType.REQUEST_GET,request, EndPointType.REPORT_AVERAGE_SALES_ENDPOINT);
        LogUtility.infoLog(response2.getStatusLine());
        response2.body().prettyPrint();
        Assert.assertEquals(response2.getStatusCode(), ResponseStatusType.RESPONSE_OK);
    }
    private Response performRequest(String requestType, RequestSpecification request, String endpoint) {
        return new RestClient().performRequest(requestType, request, endpoint);
    }
}
