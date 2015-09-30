package com.connect.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connect.R;
import com.connect.screens.HomeScreen;
import com.connect.util.ObjectGraphService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by sven on 9/29/15.
 */
public class HomeView extends RelativeLayout {
    @Bind(R.id.tvHomeLbl) TextView tvHome;

    @Inject HomeScreen.Presenter presenter;

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ObjectGraphService.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
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

    @DebugLog
    public void setHomeLabel(String sLabel) {
        tvHome.setText(sLabel);
    }
}
