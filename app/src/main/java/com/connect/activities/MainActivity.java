package com.connect.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.connect.R;
import com.connect.screens.HomeScreen;
import com.connect.util.ObjectGraphService;
import com.connect.views.FramePathContainerView;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.Flow.Traversal;
import flow.FlowDelegate;
import flow.History;
import flow.StateParceler;
import hugo.weaving.DebugLog;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import timber.log.Timber;

import static mortar.bundler.BundleServiceRunner.getBundleServiceRunner;

public class MainActivity extends AppCompatActivity implements Flow.Dispatcher {
    @Bind(R.id.drawer_layout) DrawerLayout           drawerLayout;
    //    @Bind(R.id.left_drawer)   FramePathContainerView containerDrawer;
    @Bind(R.id.main_view)     FramePathContainerView containerContent;

    @Inject Bus           bus;
    @Inject StateParceler parceler;

    private ActionBarDrawerToggle toggleDrawer;
    private FlowDelegate          flowDelegate;
    private MortarScope           activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up logging tag
        Timber.tag("MainActivity");

        // Set up Mortar
        MortarScope parentScope = MortarScope.getScope(getApplication());
        String sScope = getClass().getSimpleName() + "-task-" + getTaskId();
        activityScope = parentScope.findChild(sScope);
        if (activityScope == null) {
            activityScope = parentScope.buildChild()
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(sScope);
        }
        ObjectGraphService.inject(this, this);
        getBundleServiceRunner(activityScope).onCreate(savedInstanceState);

        // Set content view
        setContentView(R.layout.main_activity);

        // Bind views
        ButterKnife.bind(this);

        // Set up drawer toggle
        toggleDrawer = new ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            @Override
            public void onDrawerSlide(View view, float fSlideOffset) {
                super.onDrawerSlide(view, fSlideOffset);
            }

            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerStateChanged(int i) {
                // Close keyboard when opening/closing drawer
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        };

        drawerLayout.post(
            new Runnable() {
                @Override
                public void run() {
                    toggleDrawer.syncState();
                }
            }
        );

        drawerLayout.setDrawerListener(toggleDrawer);

        // Set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Set up Flow
        @SuppressWarnings("deprecation") FlowDelegate.NonConfigurationInstance nonConfig =
            (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();
        flowDelegate = FlowDelegate.onCreate(
            nonConfig, getIntent(), savedInstanceState, parceler, History.emptyBuilder().push(new HomeScreen()).build(), this);

        // Register event bus
        bus.register(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggleDrawer.syncState();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        flowDelegate.onPause();
        super.onPause();
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
        flowDelegate.onSaveInstanceState(outState);
        getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggleDrawer.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggleDrawer.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!containerContent.onBackPressed()) {
            super.onBackPressed();
        }
    }

    // Flow.Dispatcher
    @DebugLog
    @Override
    public void dispatch(Traversal traversal, Flow.TraversalCallback callback) {
        containerContent.dispatch(traversal, callback);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (flowDelegate != null) {
            Object flowService = flowDelegate.getSystemService(name);
            if (flowService != null) return flowService;
        }

        return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name)
            : super.getSystemService(name);
    }
}
