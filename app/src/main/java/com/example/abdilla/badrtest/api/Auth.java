package com.example.abdilla.badrtest.api;

import com.example.abdilla.badrtest.api.body.Login;
import com.example.abdilla.badrtest.api.body.Register;
import com.example.abdilla.badrtest.api.body.User;
import com.example.abdilla.badrtest.helper.Response;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by abdilla on 31/08/17.
 */

public interface Auth {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("freedom/auth/register")
    Observable<Response<Void>> register(@Body Register body);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("freedom/auth/login")
    Observable<Response<User>> login(@Body Login body);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("freedom/auth/logout")
    Observable<Response<Void>> logout();

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("test")
    Observable<Response<Void>> test(@Body Login body);
}
