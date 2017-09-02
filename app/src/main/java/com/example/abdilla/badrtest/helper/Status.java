package com.example.abdilla.badrtest.helper;

import java.io.Serializable;

/**
 * Created by abdilla on 6/5/16.
 */
public class Status implements Serializable {

    private String status;

    public boolean isSuccess() {
        return status.equals("success");
    }

    public void setSuccess(String status) {
        this.status = status;
    }

    public void setSuccess(boolean status) {
        this.status = status ? "success" : "failed";
    }

}

