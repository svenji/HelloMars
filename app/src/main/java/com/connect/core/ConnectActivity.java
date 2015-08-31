package com.connect.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import dagger.ObjectGraph;

/**
 * Created by sven on 8/26/15.
 */
public class ConnectActivity extends AppCompatActivity {
    protected ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Explicitly reference the application object since we don't want to match our own injector.
        ObjectGraph appGraph = Injector.obtain(getApplication());
        appGraph.inject(this);
        //        activityGraph = appGraph.plus(new ConnectActivityModule(this));
    }

    @Override
    protected void onDestroy() {
        activityGraph = null;
        super.onDestroy();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.matchesService(name)) {
            return activityGraph;
        }
        return super.getSystemService(name);
    }
}
