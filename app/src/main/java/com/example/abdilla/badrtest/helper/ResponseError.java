package com.example.abdilla.badrtest.helper;

/**
 * Created by abdilla on 6/5/16.
 */
public class ResponseError extends Exception {
    private Response response;

    public ResponseError(Response response) {
        this.response = response;
    }

    public ResponseError(String detailMessage, Response response) {
        super(detailMessage);
        this.response = response;
    }

    public ResponseError(String detailMessage, Throwable throwable, Response response) {
        super(detailMessage, throwable);
        this.response = response;
    }

    public ResponseError(Throwable throwable, Response response) {
        super(throwable);
        this.response = response;
    }

    @Override
    public String getMessage() {
        if (response != null && !isEmpty(response.getMessage()))
            return response.getMessage();

        return super.getMessage();
    }

    private boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
