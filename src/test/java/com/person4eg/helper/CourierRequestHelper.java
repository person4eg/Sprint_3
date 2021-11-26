package com.person4eg.helper;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierRequestHelper {
    public static Response createCourier(String json) {
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier");
    }

    public static Response loginCourier(String json) {
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier/login");
    }

    public static Response deleteCourier(String id) {
        return given().delete("/api/v1/courier/" + id);
    }
}
