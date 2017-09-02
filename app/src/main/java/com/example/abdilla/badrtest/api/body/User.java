package com.example.abdilla.badrtest.api.body;

/**
 * Created by abdilla on 31/08/17.
 */

public class User extends com.example.abdilla.badrtest.model.User {

    String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
