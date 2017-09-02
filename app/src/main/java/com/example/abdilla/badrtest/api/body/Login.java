package com.example.abdilla.badrtest.api.body;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abdilla on 31/08/17.
 */

public class Login implements Serializable {

    String email;
    String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

