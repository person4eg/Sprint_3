package com.person4eg.generator;

import com.person4eg.pojo.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrderGenerator {

    private static final String[] color = {"BLACK", "GREY"};

    public static Order generate() {
        return new Order(Generator.INSTANCE.getFaker().name().firstName(),
                Generator.INSTANCE.getFaker().name().lastName(),
                Generator.INSTANCE.getFaker().address().fullAddress(),
                String.valueOf(new Random().nextInt(237)+1),
                Generator.INSTANCE.getFaker().numerify("+7 9## ### ## ##"),
                new Random().nextInt(20)+1,
                new SimpleDateFormat("yyyy-MM-dd")
                        .format(Generator.INSTANCE.getFaker().date().future(365, TimeUnit.DAYS)),
                Generator.INSTANCE.getFaker().harryPotter().spell(),
                List.of(generateColor()));
    }

    public static Order generate(List<String> colors) {
        Order order = generate();
        order.setColor(colors);
        return order;
    }

    private static String generateColor() {
        return color[new Random().nextInt(color.length)];
    }
}
