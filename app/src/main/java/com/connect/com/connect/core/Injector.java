package com.connect.com.connect.core;

import android.content.Context;

import dagger.ObjectGraph;

/**
 * Created by sven on 8/26/15.
 */
public final class Injector {
    private static final String INJECTOR_SERVICE = "com.connect.InjectorService";

    @SuppressWarnings("ResourceType") // Explicitly doing a custom service.
    public static ObjectGraph obtain(Context context) {
        return (ObjectGraph)context.getSystemService(INJECTOR_SERVICE);
    }

    public static boolean matchesService(String name) {
        return INJECTOR_SERVICE.equals(name);
    }

    private Injector() {
        throw new AssertionError("No instances.");
    }
}
