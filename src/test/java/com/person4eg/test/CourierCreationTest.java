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

import static org.hamcrest.Matchers.equalTo;

public class CourierCreationTest {

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
    public void createCourierTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().assertThat()
                .body("ok",equalTo(true))
                .statusCode(201);
    }

    @Test
    public void createSameCourierTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        CourierRequestHelper.createCourier(courier.toString()).then().assertThat()
                .body("message",equalTo("Этот логин уже используется"))
                .statusCode(409);
    }

    @Test
    public void createCourierWithBlankLoginTest() {
        courier.setLogin("");
        CourierRequestHelper.createCourier(courier.toString()).then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWithBlankPasswordTest() {
        courier.setPassword("");
        CourierRequestHelper.createCourier(courier.toString()).then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWitBlankFirstNameTest() {
        courier.setFirstName("");
        CourierRequestHelper.createCourier(courier.toString()).then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutLoginTest() {
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("login");
        CourierRequestHelper.createCourier(json.toString()).then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("password");
        CourierRequestHelper.createCourier(json.toString()).then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutFirstNameTest() {
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("firstName");
        CourierRequestHelper.createCourier(json.toString()).then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWithBlankBodyTest() {
        CourierRequestHelper.createCourier("{}").then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutBodyTest() {
        CourierRequestHelper.createCourier("").then().assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }
}