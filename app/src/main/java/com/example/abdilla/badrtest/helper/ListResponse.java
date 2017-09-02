package com.example.abdilla.badrtest.helper;

import java.util.List;

/**
 * Created by abdilla on 6/11/16.
 */
public class ListResponse<T> extends Response<List<T>> {

    private int size = 0;

    public ListResponse(Boolean success, List<T> data, Error error, String message) {
        super(success, data, error,  message);
        size = data.size();
    }

    public int getSize(){
        return size;
    }
}

