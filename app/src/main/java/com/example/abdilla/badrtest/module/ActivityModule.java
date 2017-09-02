package com.example.abdilla.badrtest.module;

import android.content.Context;

import com.example.abdilla.badrtest.App;
import com.example.abdilla.badrtest.activity.BaseActivity;
import com.example.abdilla.badrtest.helper.ForActivity;
import com.example.abdilla.badrtest.helper.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abdilla on 31/08/17.
 */
@Module(
        injects = {
        },
        addsTo = ApplicationModule.class,
        library = true
)
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    @ForApplication
    App provideApplication(){
        return (App) activity.getApplication();
    }
}


