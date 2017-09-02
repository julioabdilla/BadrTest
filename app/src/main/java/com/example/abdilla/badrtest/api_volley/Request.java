package com.example.abdilla.badrtest.api_volley;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.abdilla.badrtest.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;

import rx.Observable;

/**
 * Created by abdilla on 01/09/17.
 */

public class Request {

    static String baseUrl = BuildConfig.BASE_URL;
    public static JsonObjectRequest Post(String url, final Serializable body, Response.Listener<JSONObject> listener, Response.ErrorListener error){
        return new JsonObjectRequest(baseUrl + url, new JSONObject(convertSerializableToMap(body)), listener, error){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
    }

    private static Map<String, String> convertSerializableToMap(Serializable in){
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(in);
        Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();
        return gson.fromJson(jsonString, typeOfHashMap);
    }
}
