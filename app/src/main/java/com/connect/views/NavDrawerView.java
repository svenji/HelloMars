package com.connect.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.connect.drawer.DrawerPresenter;
import com.connect.util.ObjectGraphService;

import javax.inject.Inject;

/**
 * Created by sven on 9/29/15.
 */
public class NavDrawerView extends LinearLayout {
    @Inject DrawerPresenter presenter;

    public NavDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        ObjectGraphService.inject(context, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.setDrawer(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.removeDrawer();
    }
}
