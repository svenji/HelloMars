package com.connect.android;

import android.os.Bundle;

import com.connect.screens.DrawerScreen;
import com.connect.views.MainView;

import javax.inject.Inject;

import mortar.ViewPresenter;

/**
 * Created by sven on 10/1/15.
 */
public class MainPresenter extends ViewPresenter<MainView> {
    @Inject
    MainPresenter() {
    }

    @Override
    public void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        if (!hasView()) return;
    }

    @Override
    protected void onSave(Bundle outState) {
        super.onSave(outState);
    }
}
