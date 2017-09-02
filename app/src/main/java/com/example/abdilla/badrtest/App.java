package com.example.abdilla.badrtest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.abdilla.badrtest.api.Auth;
import com.example.abdilla.badrtest.module.ApiModule;
import com.example.abdilla.badrtest.module.ApplicationModule;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by abdilla on 31/08/17.
 */

public class App extends Application {

    static public Locale locale;

    private ObjectGraph mObjectGraph;
    private RequestQueue queue;

    @Inject
    Auth authApi;

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectGraph = ObjectGraph.create(getModules().toArray());
        mObjectGraph.inject(this);

        locale = new Locale("in");

        queue = Volley.newRequestQueue(this);
    }

    private List<Object> getModules() {
        return Arrays.asList(
                new ApplicationModule(this),
                new ApiModule()
        );
    }

    public RequestQueue getQueue(){
        return queue;
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return mObjectGraph.plus(modules);
    }

    public Auth getAuthApi(){
        return authApi;
    }

    public boolean isLoggedIn(){
        return SessionManager.isLoggedIn(this);
    }

}
