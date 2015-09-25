package com.connect;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.connect.core.Injector;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by sven on 8/26/15.
 */
public class TemplateApplication extends Application {
    private ObjectGraph graph;

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

        // Dagger Setup
        graph = ObjectGraph.create(getModules().toArray());
        graph.inject(this);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.matchesService(name)) {
            return graph;
        }
        return super.getSystemService(name);
    }

    protected void initializeCrashlytics() {
    }

    protected void initializeLeakCanary() {
    }

    protected List<Object> getModules() {
        return new ArrayList<Object>();
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
