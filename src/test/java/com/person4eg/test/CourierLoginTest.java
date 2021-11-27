package com.person4eg.test;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.person4eg.generator.CourierGenerator;
import com.person4eg.helper.CourierRequestHelper;
import com.person4eg.pojo.Courier;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private Courier courier;

    @Before
    public void setup() {
        courier = CourierGenerator.generate();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void destroy() {
        JsonElement id = CourierRequestHelper.loginCourier(courier.toString()).thenReturn()
                .body().as(JsonObject.class).get("id");
        if (id != null) {
            CourierRequestHelper.deleteCourier(id.getAsString()).thenReturn();
        }
    }

    @Test
    public void loginCourierTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("id", notNullValue())
                .statusCode(200);
    }

    @Test
    public void loginCourierWithWrongLoginTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setLogin(UUID.randomUUID().toString());
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    public void loginCourierWithWrongPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setPassword(UUID.randomUUID().toString());
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    public void loginCourierWithoutLoginTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("login");
        CourierRequestHelper.loginCourier(json.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    public void loginCourierWithoutPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("password");
        CourierRequestHelper.loginCourier(json.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    public void loginCourierWithBlankLoginTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setLogin("");
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    public void loginCourierWithBlankPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setPassword("");
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    public void loginCourierWithBlankLoginAndPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setLogin("");
        courier.setPassword("");
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    public void loginCourierWithBlankBodyTest() {
        CourierRequestHelper.loginCourier("{}").then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    public void loginCourierWithoutBodyTest() {
        CourierRequestHelper.loginCourier("").then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }
}