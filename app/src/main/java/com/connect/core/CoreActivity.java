package com.connect.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.connect.util.ObjectGraphService;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by sven on 8/26/15.
 *
 * Currently unused - left here as an example for setting up a simple activity with Mortar
 */
public class CoreActivity extends AppCompatActivity {
    @Inject Bus bus;

    protected MortarScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ObjectGraphService.inject(this, this);

        // Register event bus
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        // Unregister event bus
        bus.unregister(this);

        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing() && activityScope != null) {
            activityScope.destroy();
            activityScope = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        String sScope = getClass().getSimpleName() + "-task-" + getTaskId();
        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), sScope);
        if (activityScope == null) {
            activityScope = MortarScope.buildChild(getApplicationContext())
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(sScope);
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
            : super.getSystemService(name);
    }
}
