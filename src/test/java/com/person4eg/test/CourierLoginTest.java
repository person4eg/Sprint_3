package com.person4eg.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.person4eg.generator.CourierGenerator;
import com.person4eg.helper.CourierRequestHelper;
import com.person4eg.pojo.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Проверка авторизации курьера с правильным логином и паролем")
    @Description("При авторизации курьера с правильным логином и паролем, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("id", notNullValue())
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным логином")
    @Description("При авторизации курьера с неправильным логином, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithWrongLoginTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setLogin(UUID.randomUUID().toString());
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным паролем")
    @Description("При авторизации курьера с неправильным паролем, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithWrongPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setPassword(UUID.randomUUID().toString());
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без использования поля логин")
    @Description("При авторизации курьера поле с логином не передается в запрос, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithoutLoginTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("login");
        CourierRequestHelper.loginCourier(json.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без использования поля пароля")
    @Description("При авторизации курьера поле с паролем не передается в запрос, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithoutPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        JsonObject json = new Gson().toJsonTree(courier).getAsJsonObject();
        json.remove("password");
        CourierRequestHelper.loginCourier(json.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с пустым логином")
    @Description("При авторизации курьера в поле с логином передается строка с нулевым количеством символов, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithBlankLoginTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setLogin("");
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с пустым паролем")
    @Description("При авторизации курьера в поле с паролем передается строка с нулевым количеством символов, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithBlankPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setPassword("");
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с пустым логином и паролем")
    @Description("При авторизации курьера в поле с логином и паролем передается строка с нулевым количеством символов, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithBlankLoginAndPasswordTest() {
        CourierRequestHelper.createCourier(courier.toString()).then().statusCode(201);
        courier.setLogin("");
        courier.setPassword("");
        CourierRequestHelper.loginCourier(courier.toString()).then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с пустым телом запроса")
    @Description("При авторизации курьера в тело запроса передается строка с нулевым количеством символов, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithBlankBodyTest() {
        CourierRequestHelper.loginCourier("{}").then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без использования полей логина и пароля")
    @Description("При авторизации курьера передается пустой запрос, " +
            "проверяется ожидаемое тело ответа и код статуса")
    public void loginCourierWithoutBodyTest() {
        CourierRequestHelper.loginCourier("").then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }
}