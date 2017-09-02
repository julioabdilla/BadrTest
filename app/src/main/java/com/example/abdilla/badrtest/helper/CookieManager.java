package com.example.abdilla.badrtest.helper;

/**
 * Created by abdilla on 01/09/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by abook23 on 2016/11/18.
 * Versions 1.0
 */

public class CookieManager implements CookieJar {

    private SharedPreferences spf;
    private static final String KEY_COOKIE_PREFERENCES = "COOKIE_PREFERENCES";
    private static final String KEY_cookie = "cookie";
    private static List<Cookie> cookies;

    public CookieManager(Context applicationContext) {
        spf = applicationContext.getSharedPreferences(KEY_COOKIE_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            Gson gson = new Gson();
            String cookiesStr = gson.toJson(cookies);
            SharedPreferences.Editor edit = spf.edit();
            edit.putString(KEY_cookie, cookiesStr);
            edit.apply();
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (cookies == null) {
            String cookiesStr = spf.getString(KEY_cookie, null);
            if (cookiesStr != null) {
                cookies = new Gson().fromJson(cookiesStr, new TypeToken<List<Cookie>>() {
                }.getType());
            } else {
                cookies = new ArrayList<>();
            }
        }
        return cookies;
    }
}