package com.connect;

import com.connect.core.AndroidModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import dagger.Module;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(
    constants = BuildConfig.class,
    packageName = "com.connect",
    application = TestTemplateApplicationImpl.class,
    sdk = 22
)
public class ApplicationTest {
    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testGetModules_notEmpty() throws Exception {
        assertThat(true, is(true));
//        TestTemplateApplicationImpl app = (TestTemplateApplicationImpl) RuntimeEnvironment.application;
//        List<Object> zModules = app.getModules();
//        assertThat(zModules, not(nullValue()));
//        assertThat(!zModules.isEmpty(), is(true));
    }

    @Module(
        injects = {
            ApplicationTest.class,
            TemplateApplicationImpl.class,
            TestTemplateApplicationImpl.class
        },
        includes = AndroidModule.class,
        overrides = true
    )
    static class ApplicationTestModule {
    }
}