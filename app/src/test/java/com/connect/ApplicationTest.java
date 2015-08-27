package com.connect;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        packageName = "com.connect"
)
public class ApplicationTest {
    @Before
    public void setup() throws Exception {
    }

    @Test
    public void test() throws Exception {
        assertThat(false, equalTo(true));
    }
}