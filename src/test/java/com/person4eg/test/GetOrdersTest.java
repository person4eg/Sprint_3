package com.person4eg.test;

import com.person4eg.helper.OrderRequestHelper;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetOrdersTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createCourierTest() {
        OrderRequestHelper.getOrders(null, null, null, null).then().assertThat()
                .body("orders", is(not(empty())))
                .statusCode(200);
    }
}