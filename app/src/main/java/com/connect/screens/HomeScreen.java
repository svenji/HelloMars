package com.connect.screens;

import android.os.Bundle;

import com.connect.R;
import com.connect.core.RootModule;
import com.connect.util.Layout;
import com.connect.util.WithModule;
import com.connect.views.HomeView;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import flow.path.Path;
import mortar.ViewPresenter;
import timber.log.Timber;

/**
 * Created by sven on 9/29/15.
 */

@Layout(R.layout.home_view)
@WithModule(HomeScreen.Module.class)
public class HomeScreen extends Path {
    @dagger.Module(injects = HomeView.class, addsTo = RootModule.class)
    public class Module {
        @Provides
        String provideLabel() {
            return "HELLO WORLD!";
        }
    }

    @Singleton
    public static class Presenter extends ViewPresenter<HomeView> {
        private final String label;

        @Inject
        Presenter(String label) {
            this.label = label;
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView()) return;
//            if (savedInstanceState != null) {
//            }
            getView().setHomeLabel(label);
        }

        // Can typically avoid using onSave
//        @Override
//        protected void onSave(Bundle outState) {
//            super.onSave(outState);
//        }
    }
}
