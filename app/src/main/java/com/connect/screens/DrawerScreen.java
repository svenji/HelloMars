package com.connect.screens;

import android.graphics.Color;
import android.os.Bundle;

import com.connect.views.DrawerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;
import mortar.ViewPresenter;

/**
 * Created by sven on 9/29/15.
 */
public class DrawerScreen extends Path {
    @dagger.Module(library = true, complete = false)
    public static class Module {
    }

    @Singleton
    public static class Presenter extends ViewPresenter<DrawerView> {
        @Inject
        Presenter() {
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView()) return;
            getView().setBackgroundColor(Color.WHITE);
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
        }
    }
}
