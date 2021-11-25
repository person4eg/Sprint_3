package com.person4eg.generator;

import com.person4eg.pojo.Courier;

import java.util.Random;
import java.util.UUID;

public class CourierGenerator {

    private static final String[] names = {"Александр", "Михаил", "Дмитрий", "Даниил", "Иван", "Кирилл", "Роман",
            "Тимофей", "Егор", "Максим", "Анна", "Мария", "Александра", "Софья", "Алиса", "Дарья", "Виктория", "Елена",
            "Василиса", "Полина"};

    public static Courier generate() {
        String login = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String firstName = names[new Random().nextInt(names.length)];
        return new Courier(login, password, firstName);
    }
}
