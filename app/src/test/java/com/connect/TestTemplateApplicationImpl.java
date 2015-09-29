package com.connect;

import android.support.annotation.NonNull;

import com.connect.core.AndroidModule;
import com.connect.core.RootModule;
import com.connect.util.ObjectGraphService;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import mortar.MortarScope;

/**
 * Created by sven on 8/28/15.
 */
public class TestTemplateApplicationImpl extends TemplateApplicationImpl {
    @Override
    protected void initializeCrashlytics() {

    }

    @Override
    protected void initializeLeakCanary() {

    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (rootScope == null) {
            rootScope = MortarScope.buildRootScope()
                .withService(
                    ObjectGraphService.SERVICE_NAME,
                    ObjectGraph.create(new RootModule(),
                                       new AndroidModule(this),
                                       new ApplicationTest.ApplicationTestModule()))
                .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }
}