package com.example.abdilla.badrtest.module;

import android.app.Application;
import android.content.Context;

import com.example.abdilla.badrtest.App;
import com.example.abdilla.badrtest.helper.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abdilla on 31/08/17.
 */

@Module(library = true,
        injects = {
                App.class
        },
        includes = {
                ApiModule.class,
                BusModule.class
        })
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ForApplication
    public Context provideApplicationContext() {
        return mApplication;
    }
}

