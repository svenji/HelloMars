package com.connect;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by sven on 9/17/15.
 */
public class TemplateApplicationImpl extends TemplateApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // Crashlytics
        initializeCrashlytics();
    }

    @Override
    protected void initializeCrashlytics() {
        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = super.getModules();
        modules.add(new AndroidModule(this));
        return modules;
    }
}
