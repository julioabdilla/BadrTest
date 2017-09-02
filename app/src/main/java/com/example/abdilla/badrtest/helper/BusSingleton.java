package com.example.abdilla.badrtest.helper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by abdilla on 31/08/17.
 */

public class BusSingleton {
    private static Bus global;

    public static Bus getGlobal() {
        if (global == null)
            global = new Bus(ThreadEnforcer.ANY);

        return global;
    }
}
