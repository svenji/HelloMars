package com.connect.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.connect.screens.DrawerScreen;
import com.connect.util.ObjectGraphService;

import javax.inject.Inject;

/**
 * Created by sven on 9/29/15.
 */
public class DrawerView extends LinearLayout {
    @Inject DrawerScreen.Presenter presenter;
    public DrawerView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        ObjectGraphService.inject(context, this);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        ObjectGraphService.inject(context, this);
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
}
