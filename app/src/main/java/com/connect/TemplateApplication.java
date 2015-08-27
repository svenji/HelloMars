package com.connect;

import android.app.Application;
import android.support.annotation.NonNull;

import com.connect.com.connect.core.Injector;

import dagger.ObjectGraph;

/**
 * Created by sven on 8/26/15.
 */
public class TemplateApplication extends Application {
    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        // Crashlytics
        //        Fabric.with(this, new Crashlytics());

        // Leak Canary
        //        LeakCanary.install(this);

        // Dagger Setup
        graph = ObjectGraph.create(new AndroidModule(this));
        graph.inject(this);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.matchesService(name)) {
            return graph;
        }
        return super.getSystemService(name);
    }
}
