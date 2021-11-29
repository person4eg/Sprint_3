package com.person4eg.test;

import com.person4eg.generator.OrderGenerator;
import com.person4eg.helper.OrderRequestHelper;
import com.person4eg.pojo.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private final Order order;
    private String responseBody;

    public  OrderCreationTest(Order order) {
        this.order = order;
    }

    @Before
    public void setup() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void destroy() {
        if (responseBody != null) {
            //api отмены заказа не работает, но во всяком случае написал код отмены тестовых действий
            OrderRequestHelper.cancelOrder(responseBody).thenReturn();
        }
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {OrderGenerator.generate(List.of())},
                {OrderGenerator.generate(List.of("BLACK"))},
                {OrderGenerator.generate(List.of("GREY"))},
                {OrderGenerator.generate(List.of("GREY", "BLACK"))}
        };
    }

    @Test
    @DisplayName("Check status code and body when creating order")
    public void orderCreationTest() {
        Response response = OrderRequestHelper.createOrder(order.toString());
        response.then().assertThat().body("track", notNullValue()).statusCode(201);
        this.responseBody = response.body().asString();
    }
}
