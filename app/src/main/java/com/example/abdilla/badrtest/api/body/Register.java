package com.example.abdilla.badrtest.api.body;

import java.io.Serializable;

/**
 * Created by abdilla on 31/08/17.
 */

public class Register implements Serializable{
    String email;
    String password;
    String confirmation_password;

    public Register(String email, String password, String confirmation_password) {
        this.email = email;
        this.password = password;
        this.confirmation_password = confirmation_password;
    }
}
