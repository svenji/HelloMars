package com.connect.core;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.connect.BuildConfig;
import com.connect.util.ObjectGraphService;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import mortar.MortarScope;
import timber.log.Timber;

/**
 * Created by sven on 8/26/15.
 */
public class TemplateApplication extends Application {
    protected MortarScope rootScope;

    @Inject
    public TemplateApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (rootScope == null) {
            rootScope = MortarScope.buildRootScope()
                .withService(ObjectGraphService.SERVICE_NAME, ObjectGraph.create(new RootModule()))
                .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }

    protected void initializeCrashlytics() {
    }

    protected void initializeLeakCanary() {
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(
            int priority,
            String tag,
            String message,
            Throwable throwable
        ) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            Crashlytics.log(priority, tag, message);

            if (throwable != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(throwable);
                } else if (priority == Log.WARN) {
                    Crashlytics.logException(throwable);
                }
            }
        }
    }
}
