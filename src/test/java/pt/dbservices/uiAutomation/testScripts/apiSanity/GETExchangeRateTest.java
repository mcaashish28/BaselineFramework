package pt.dbservices.uiAutomation.testScripts.apiSanity;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pt.dbservices.apiAutomation.data.ExchangeRateDates;
import pt.dbservices.apiAutomation.utils.RequestManager;
import pt.dbservices.base.BaseTest;
import pt.dbservices.utilities.reports.Results;

public class GETExchangeRateTest extends BaseTest {
    private Logger log = LogManager.getLogger(GETExchangeRateTest.class.getName());

    @Test(description = "API - Get Latest Exchange Rate.")
    public void getLatestExchangeRateTest(){
        Results.setName("API - Get Latest Exchange Rate.");

        Response response = RequestManager.getExchangeRate(ExchangeRateDates.latest.toString());

        log.info("Response code is : "+ response.statusCode());
        log.info("Response for Latest Exchange Rate is : " + response.asString());
        // Report in HTML Result
        Assert.assertEquals(response.statusCode(),200, "Response Code is not 200 OK. Response Code is : " + response.statusCode());
        Results.pass("Response code is : '"+ response.statusCode() + "'");
        Results.pass("Response for Latest Exchange Rate is : '" + response.asString() + "'");
    }

    @Test(description = "API - Get Specific Date Exchange Rate.")
    public void getSpecificDateExchangeRateTest(){
        Results.setName("API - Get Specific Exchange Rate.");

        String date = ExchangeRateDates.Date_2010_01_12.toString();
        String[] arrDate = date.split("_");
        String resDate = arrDate[1] +"-"+ arrDate[2] +"-"+ arrDate[3];

        Response response = RequestManager.getExchangeRate(resDate);

        log.info("Response code is : "+ response.statusCode());
        log.info("Response for Latest Exchange Rate is : " + response.asString());
        // Report in HTML Result
        Assert.assertEquals(response.statusCode(),200, "Response Code is not 200 OK. Response Code is : " + response.statusCode());
        Results.pass("Response code is : '"+ response.statusCode() + "'");
        Results.pass("Response for Latest Exchange Rate is : '" + response.asString() + "'");
    }

}
