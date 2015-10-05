package com.connect;

import android.support.annotation.NonNull;

import com.connect.android.AndroidModule;
import com.connect.core.RootModule;
import com.connect.core.TemplateApplication;
import com.connect.util.ObjectGraphService;
import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;

import dagger.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import mortar.MortarScope;
import timber.log.Timber;

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
    protected void initializeCrashlytics() {
        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void initializeLeakCanary() {
        ExcludedRefs.Builder excludedRefsBuilder = AndroidExcludedRefs.createAppDefaults();
        // Workaround for excluding Google Play Services leaks
        //        excludedRefsBuilder.staticField("com.google.android.chimera.container.a", "a");
        //        excludedRefsBuilder.staticField("com.google.android.gms.location.internal.t", "a");
        LeakCanary.install(this, DisplayLeakService.class, excludedRefsBuilder.build());
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (rootScope == null) {
            Timber.i("Running Impl");
            rootScope = MortarScope.buildRootScope()
                .withService(ObjectGraphService.SERVICE_NAME, ObjectGraph.create(new RootModule(), new AndroidModule(this)))
                .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }
}
