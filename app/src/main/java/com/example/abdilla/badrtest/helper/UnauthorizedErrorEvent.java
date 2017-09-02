package com.example.abdilla.badrtest.helper;

/**
 * Created by abdilla on 31/08/17.
 */

public class UnauthorizedErrorEvent {
    private Throwable exception;

    public UnauthorizedErrorEvent(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}
