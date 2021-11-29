package com.person4eg.helper;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderRequestHelper {

    public static Response createOrder(String json) {
        return given()
                .config(RestAssuredHelper.config())
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/orders");
    }

    public static Response cancelOrder(String json) {
        return given()
                .config(RestAssuredHelper.config())
                .body(json)
                .put("/api/v1/orders/cancel");
    }

    public static Response getOrders(Integer courierId, String[] nearestStations, Integer limit, Integer page) {
        return given()
                .config(RestAssuredHelper.config())
                .get("api/v1/orders" + prepareParamsForGetOrder(courierId, nearestStations, limit, page));
    }

    private static String prepareParamsForGetOrder(Integer courierId, String[] nearestStations,
                                                   Integer limit, Integer page) {
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        if (courierId != null) {
            builder.append("?courierId=").append(courierId);
            isFirst = false;
        }
        if (nearestStations != null) {
            builder.append(isFirst ? "?" : "&").append("nearestStation=").append(new Gson().toJson(nearestStations));
            isFirst = false;
        }
        if (limit != null) {
            builder.append(isFirst ? "?" : "&").append("limit=").append(limit);
            isFirst = false;
        }
        if (page != null) {
            builder.append(isFirst ? "?" : "&").append("page=").append(page);
        }
        return builder.toString();
    }
}
