package com.connect.drawer;

import android.content.Context;
import android.graphics.Color;

import com.connect.views.NavDrawerView;

import javax.inject.Singleton;

import dagger.Provides;
import mortar.Presenter;
import mortar.bundler.BundleService;

import static mortar.bundler.BundleService.getBundleService;

/**
 * Created by sven on 9/29/15.
 */
public class DrawerPresenter extends Presenter<DrawerPresenter.Activity> {
    private NavDrawerView drawerView;

    @Override
    protected BundleService extractBundleService(Activity activity) {
        return getBundleService(activity.getContext());
    }

    public interface Activity {
        Context getContext();
    }

    @dagger.Module(injects = {NavDrawerView.class}, library = true)
    public static class Module {
        @Provides
        @Singleton
        DrawerPresenter provideDrawerPresenter() {
            return new DrawerPresenter();
        }
    }

    public void setDrawer(NavDrawerView v) {
        drawerView = v;
    }

    public void removeDrawer() {
        drawerView = null;
    }
}
