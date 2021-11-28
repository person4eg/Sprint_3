package com.person4eg.generator;

import com.person4eg.pojo.Courier;

import java.util.UUID;

public class CourierGenerator {

    public static Courier generate() {
        String login = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String firstName = SimpleDataGenerator.generateFirstName();
        return new Courier(login, password, firstName);
    }
}
