package pt.dbservices.apiAutomation.utils;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pt.dbservices.apiAutomation.data.ExchangeRateDates;
import pt.dbservices.utilities.reports.Results;

import static io.restassured.RestAssured.given;
import static pt.dbservices.base.BaseTest.envProps;

public class RequestManager {

    private static ThreadLocal<RequestSpecification> request = new ThreadLocal<>();


    public static void deleteRequestFromThreadLocal() {
        if(request.get() != null)
            request.remove();
    }

    public static String getAPIBaseURL(){
         return envProps.getProperty("apiBaseURL") + envProps.getProperty("apiBasePath");
    }

    public static Response getExchangeRate(String date){
        Response response = null;
        response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(getAPIBaseURL() +"/" + date);
        Results.info("GET - API Request for URI : '"+ getAPIBaseURL() +"/" + date + "'");
        return response;
    }

}
