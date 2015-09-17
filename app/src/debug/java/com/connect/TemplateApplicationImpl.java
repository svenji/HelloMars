package com.connect;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;

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

        // Leak Canary
        initializeLeakCanary();
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = super.getModules();
        modules.add(new AndroidModule(this));
        return modules;
    }

    protected void initializeCrashlytics() {
        Fabric.with(this, new Crashlytics());
    }

    protected void initializeLeakCanary() {
        ExcludedRefs.Builder excludedRefsBuilder = AndroidExcludedRefs.createAppDefaults();
        // Workaround for excluding Google Play Services leaks
        //        excludedRefsBuilder.staticField("com.google.android.chimera.container.a", "a");
        //        excludedRefsBuilder.staticField("com.google.android.gms.location.internal.t", "a");
        LeakCanary.install(this, DisplayLeakService.class, excludedRefsBuilder.build());
    }
}
