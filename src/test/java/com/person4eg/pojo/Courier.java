package com.person4eg.pojo;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Courier {

    private String login;
    private String password;
    private String firstName;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}