package com.connect.core;

/**
 * Created by sven on 8/26/15.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.connect.TemplateApplicationImpl;
import com.connect.screens.DrawerScreen;
import com.connect.views.DrawerView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for all Android related provisions
 */
@Module(library = true,
    injects = {
        TemplateApplicationImpl.class
    },
    addsTo = RootModule.class
)
public class AndroidModule {
    private final TemplateApplicationImpl app;

    public AndroidModule(TemplateApplicationImpl app) {
        this.app = app;
    }

    @Provides
    @Singleton
    TemplateApplicationImpl provideTemplateApplication() {
        return this.app;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return this.app.getApplicationContext();
    }

    @Provides
    SharedPreferences provideDefaultSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    NotificationManager provideNotificationManager(final Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
