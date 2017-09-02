package com.example.abdilla.badrtest.module;

import com.example.abdilla.badrtest.helper.BusSingleton;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abdilla on 31/08/17.
 */

@Module(library = true, complete = false)
public class BusModule {

    @Provides
    @Singleton
    Bus provideGlobalBus() {
        return BusSingleton.getGlobal();
    }
}
