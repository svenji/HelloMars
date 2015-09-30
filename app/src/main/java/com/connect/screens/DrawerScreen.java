package com.connect.screens;

import android.os.Bundle;

import com.connect.R;
import com.connect.core.RootModule;
import com.connect.util.Layout;
import com.connect.util.WithModule;
import com.connect.views.DrawerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;
import mortar.ViewPresenter;

/**
 * Created by sven on 9/29/15.
 */
@Layout(R.layout.home)
@WithModule(DrawerScreen.Module.class)
public class DrawerScreen extends Path {
    @dagger.Module(injects = DrawerView.class, addsTo = RootModule.class, library = true, complete = false)
    public class Module {
    }

    @Singleton
    public static class Presenter extends ViewPresenter<DrawerView> {
        private int counter;

        @Inject
        Presenter() {
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView()) return;
            counter = savedInstanceState.getInt("counter");
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            outState.putInt("counter", ++counter);
        }
    }
}
