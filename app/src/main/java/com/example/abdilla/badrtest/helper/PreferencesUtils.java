package com.example.abdilla.badrtest.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by abdilla on 31/08/17.
 */

public class PreferencesUtils {

    private static String name="TEST";

    public static void putString(Context context, String key, String isi) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putString(key, isi);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        String isi;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        isi = sharedPreferences.getString(key, null);
        return isi;
    }

    public static void putDouble(Context context, String key, double isi) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, (float) isi);
        editor.apply();
    }

    public static Double getDouble(Context context, String key) {
        double isi;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        switch (key) {
            case "lat":
                isi = sharedPreferences.getFloat(key, -7.7521492f);
                break;
            case "lon":
                isi = sharedPreferences.getFloat(key, 110.377659f);
                break;
            default:
                isi = sharedPreferences.getFloat(key, 0);
        }

        return isi;
    }

    public static void putBoolean(Context context, String key, boolean isi) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, isi);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, def);
    }

    public static void putInt(Context context, String key, int isi) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putInt(key, isi);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        return sharedPreferences.getInt(key, 0);
    }

    public static void putLong(Context context, String key, int isi) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putLong(key, isi);
        editor.apply();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        return sharedPreferences.getLong(key, 0);
    }

    public static void putObject(Context context, String key, Object isi){
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(isi);
        editor.putString(key, json);
        editor.apply();
    }

    public static <T> T getObject(Context context, String key, Class clss){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        try {
            T object = (T)gson.fromJson(json, clss);
            return object;
        }catch (Exception e){
            return null;
        }
    }

    public static void putStringSet(Context context, String key, Set<String> isi){
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putStringSet(key, isi);
        editor.apply();
    }

    public static Set<String> getStringSet(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(key, new HashSet<String>());
    }

    public static void addStringList(Context context, String key, String isi){
        Set<String> set = new HashSet<>(getStringList(context, key));
        set.add(isi);
        putStringSet(context, key, set);
    }

    public static void putStringList(Context context, String key, ArrayList<String> isi){
        Set<String> set = new HashSet<>(isi);
        putStringSet(context, key, set);
    }

    public static ArrayList<String> getStringList(Context context, String key){
        return new ArrayList<>(getStringSet(context, key));
    }
}

