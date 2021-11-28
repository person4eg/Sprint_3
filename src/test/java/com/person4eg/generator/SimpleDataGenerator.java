package com.person4eg.generator;

import java.util.Random;

public class SimpleDataGenerator {

    private static final String[] firstNames = {"Александр", "Михаил", "Дмитрий", "Даниил", "Иван", "Кирилл", "Роман",
            "Тимофей", "Егор", "Максим"};

    private static final String[] lastName = {"Иванов", "Васильев", "Петров", "Смирнов", "Михайлов", "Фёдоров",
            "Соколов", "Яковлев", "Попов", "Андреев", "Алексеев", "Александров", "Лебедев", "Григорьев", "Степанов"};

    private static final String[] color = {"BLACK", "GREY"};

    private static final String[] streets = {"Центральная", "Молодежная", "Школьная", "Лесная", "Садовая", "Советская",
            "Новая", "Набережная", "Заречная", "Зеленая"};

    public static String generateFirstName() {
        return firstNames[new Random().nextInt(firstNames.length)];
    }

    public static String generateLastName() {
        return lastName[new Random().nextInt(firstNames.length)];
    }

    public static String generateColor() {
        return color[new Random().nextInt(color.length)];
    }

    public static String generateAddress() {
        return "ул. " + streets[new Random().nextInt(color.length)] + " д. " + (new Random().nextInt(20) + 1);
    }

    public static String generatePhoneNumber() {
        return "+7 " + intToString(new Random().nextInt(1000), 3)
                + " " + intToString(new Random().nextInt(1000), 3)
                + " " + intToString(new Random().nextInt(100), 2)
                + " " + intToString(new Random().nextInt(100), 2);
    }

    public static String generateDate() {
        return "2022-" + intToString(new Random().nextInt(12) + 1, 2)
                + "-" + intToString(new Random().nextInt(28) + 1, 2);
    }

    private static String intToString(int number, int size) {
        if (number > 0) {
            size = size - (int) Math.log10(number) - 1;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("0".repeat(Math.max(0, size)));
        if (number > 0) {
            builder.append(number);
        }
        return builder.toString();
    }
}