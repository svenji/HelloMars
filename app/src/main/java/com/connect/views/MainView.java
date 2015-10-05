package com.connect.views;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.connect.R;
import com.connect.android.MainPresenter;
import com.connect.screens.DrawerScreen;
import com.connect.util.ObjectGraphService;
import com.connect.util.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by sven on 10/1/15.
 */
public class MainView extends DrawerLayout {
    @Inject MainPresenter presenter;

    private ActionBarDrawerToggle toggleDrawer;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ObjectGraphService.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        initDrawer();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    private void initDrawer() {
        // Set up drawer toggle
        toggleDrawer = new ActionBarDrawerToggle(
            (Activity) getContext(),
            this,
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
                ((Activity) getContext()).invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ((Activity) getContext()).invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerStateChanged(int i) {
                // Close keyboard when opening/closing drawer
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(((Activity) getContext()).getWindow().getDecorView().getWindowToken(), 0);
            }
        };

        post(
            new Runnable() {
                @Override
                public void run() {
                    toggleDrawer.syncState();
                }
            }
        );
        setDrawerListener(toggleDrawer);

        // Add Drawer Screen
        View vDrawer = Utils.inflateScreenView(getContext(), DrawerScreen.class);

        // Set some basic layout parameters so the drawer works
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(
            getContext().getResources().getDimensionPixelSize(R.dimen.drawer_width),
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.START;
        vDrawer.setLayoutParams(params);
        addView(vDrawer, 1);
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return toggleDrawer;
    }
}
