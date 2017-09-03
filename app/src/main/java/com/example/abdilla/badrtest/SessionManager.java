package com.example.abdilla.badrtest;

import android.content.Context;

import com.example.abdilla.badrtest.helper.PreferencesUtils;
import com.example.abdilla.badrtest.model.User;

/**
 * Created by abdilla on 31/08/17.
 */

public class SessionManager {

    public static void setAccessToken(Context context, String token){
        PreferencesUtils.putString(context, Keys.SESSION_ACCESS_TOKEN, token);
    }

    public static String getAccessToken(Context context){
        return PreferencesUtils.getString(context, Keys.SESSION_ACCESS_TOKEN);
    }

    public static void setUser(Context context, User user){
        PreferencesUtils.putObject(context, Keys.SESSION_USER, user);
    }

    public static User getUser(Context context){
        return PreferencesUtils.getObject(context, Keys.SESSION_USER, User.class);
    }

    public static boolean isLoggedIn(Context context){
        return getAccessToken(context)!=null&&getUser(context)!=null;
    }

    public static void clearSession(Context context){
        PreferencesUtils.clear(context, Keys.SESSION_ACCESS_TOKEN);
        PreferencesUtils.clear(context, Keys.SESSION_USER);
    }
}
