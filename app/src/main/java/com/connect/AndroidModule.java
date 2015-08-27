package com.connect;

/**
 * Created by sven on 8/26/15.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for all Android related provisions
 */
@Module(library = true,
    injects = {
            TemplateApplication.class
    },
    includes = {
            TemplateModule.class
    }
)
public class AndroidModule {
    private final TemplateApplication app;

    public AndroidModule(TemplateApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    TemplateApplication provideTemplateApplication() {
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
