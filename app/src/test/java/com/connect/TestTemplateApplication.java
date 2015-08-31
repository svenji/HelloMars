package com.connect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sven on 8/28/15.
 */
public class TestTemplateApplication extends TemplateApplication {
    @Override
    protected void initializeCrashlytics() {

    }

    @Override
    protected void initializeLeakCanary() {

    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<Object>();
        modules.add(new AndroidModule(this));
        modules.add(new ApplicationTest.ApplicationTestModule());
        return modules;
    }
}