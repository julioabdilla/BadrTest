package com.example.abdilla.badrtest.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Created by abdilla on 6/5/16.
 */
public class Response<T> {
    private JsonPrimitive success;
    private T data;
    private Error error;
    private JsonElement message;

    public Response(Boolean success, T data, Error error, String message) {
        this.success = new JsonPrimitive(success);
        this.data = data;
        this.error = error;
        this.message = new JsonPrimitive(message);
    }

    public static <R> R read(Response<R> response) throws ResponseError {
        if (!response.isSuccess()) {
            throw new ResponseError(response);
        }

        return response.getResult();
    }

    public Boolean isSuccess(){
        return success.isJsonPrimitive() && success.getAsJsonPrimitive().getAsBoolean();
    }

    public T getResult() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public String getMessage() {
        if (message.isJsonPrimitive())
            return message.getAsJsonPrimitive().getAsString();
        else
            return message.toString();
    }

    class Error{
        int code;
        String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
