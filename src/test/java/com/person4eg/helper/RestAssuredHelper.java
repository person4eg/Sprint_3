package com.person4eg.helper;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.apache.http.params.CoreConnectionPNames;

public class RestAssuredHelper {

    @SuppressWarnings("deprecation")
    public static RestAssuredConfig config(){
        return RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 2000));
    }
}
