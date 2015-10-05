package com.connect;

import com.connect.android.AndroidModule;
import com.connect.core.TemplateApplication;
import com.crashlytics.android.Crashlytics;

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
    public Object getSystemService(@NonNull String name) {
        if (rootScope == null) {
            rootScope = MortarScope.buildRootScope()
                .withService(ObjectGraphService.SERVICE_NAME, ObjectGraph.create(new RootModule(), new AndroidModule(this)))
                .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }
}
