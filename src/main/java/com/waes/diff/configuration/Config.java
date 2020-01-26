package com.waes.diff.configuration;

import com.google.inject.Injector;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Holds the injectors available for further usage
 */
public abstract class Config {

    final static ConcurrentMap<String, Injector> injectors = new ConcurrentHashMap<>();

    public static void addInjector(String name, Injector injector) {
        injectors.put(name, injector);
    }

    public static Injector getInjector(String name) {
        return injectors.get(name);
    }

}
