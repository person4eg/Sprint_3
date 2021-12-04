package com.person4eg.test;

import com.person4eg.helper.OrderRequestHelper;
import com.person4eg.utils.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetOrdersTest {

    @Before
    public void setup() {
        RestAssured.baseURI = Constants.baseURI;
    }

    @Test
    @DisplayName("Проверка списка созданных заказов")
    @Description("Про создании запроса проверяет тело ответа не пустое и код статуса")
    public void returnOrdersListTest() {
        OrderRequestHelper.getOrders(null, null, null, null).then().assertThat()
                .body("orders", is(not(empty())))
                .statusCode(200);
    }
}