package com.example.abdilla.badrtest.helper;

/**
 * Created by abdilla on 31/08/17.
 */

public class ConnectionErrorEvent {
    private Throwable exception;

    public ConnectionErrorEvent(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}

