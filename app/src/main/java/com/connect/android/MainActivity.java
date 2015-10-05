package com.connect.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.connect.R;
import com.connect.android.ActionBarOwner;
import com.connect.screens.HomeScreen;
import com.connect.util.ObjectGraphService;
import com.connect.views.FramePathContainerView;
import com.connect.views.MainView;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.Flow.Traversal;
import flow.FlowDelegate;
import flow.History;
import flow.StateParceler;
import flow.path.Path;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import timber.log.Timber;

import static mortar.bundler.BundleServiceRunner.getBundleServiceRunner;

public class MainActivity extends AppCompatActivity implements Flow.Dispatcher, ActionBarOwner.Activity {
    @Bind(R.id.drawer_layout)  MainView               vMain;
    @Bind(R.id.main_container) FramePathContainerView container;

    @Inject Bus            bus;
    @Inject StateParceler  parceler;
    @Inject ActionBarOwner actionBarOwner;

    private ActionBarDrawerToggle toggleDrawer;
    private FlowDelegate          flowDelegate;
    private MortarScope           activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set logging tag
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
        setContentView(R.layout.main_view);

        // Bind views
        ButterKnife.bind(this);

        // Set up drawer toggle
        toggleDrawer = vMain.getDrawerToggle();

        // Set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarOwner.takeView(this);

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
        actionBarOwner.dropView(this);
        actionBarOwner.setConfig(null);

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
        if (item.getItemId() == android.R.id.home) {
            return container.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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

    // BackSupport
    @Override
    public void onBackPressed() {
        if (!container.onBackPressed()) {
            super.onBackPressed();
        }
    }

    // Flow.Dispatcher
    @Override
    public void dispatch(Traversal traversal, Flow.TraversalCallback callback) {
        Path newScreen = traversal.destination.top();
        String title = newScreen.getClass().getSimpleName();
        actionBarOwner.setConfig(new ActionBarOwner.Config(true, newScreen instanceof HomeScreen, title));
        container.dispatch(traversal, callback);
    }

    // ActionBarOwner.Activity
    @Override
    public void setDisplayShowHomeEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(enabled);
        }
    }

    @Override
    public void setHomeButtonEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enabled);
            actionBar.setHomeButtonEnabled(enabled);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
