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

        private int counter = 0;

        @Inject
        Presenter(String label) {
            this.label = label;
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView()) return;
            if (savedInstanceState != null) {
                counter = savedInstanceState.getInt("counter");
            }
            Timber.i("counter = " + counter);

            getView().setHomeLabel(label + " " + counter);
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            outState.putInt("counter", ++counter);
        }
    }
}