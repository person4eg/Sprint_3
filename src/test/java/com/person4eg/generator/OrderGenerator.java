package com.person4eg.generator;

import com.person4eg.pojo.Order;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OrderGenerator {

    public static Order generate() {
        return new Order(SimpleDataGenerator.generateFirstName(),
                SimpleDataGenerator.generateLastName(),
                SimpleDataGenerator.generateAddress(),
                String.valueOf(new Random().nextInt(237)+1),
                SimpleDataGenerator.generatePhoneNumber(),
                new Random().nextInt(20)+1,
                SimpleDataGenerator.generateDate(),
                UUID.randomUUID().toString(),
                List.of(SimpleDataGenerator.generateColor()));
    }

    public static Order generate(List<String> colors) {
        Order order = generate();
        order.setColor(colors);
        return order;
    }
}
