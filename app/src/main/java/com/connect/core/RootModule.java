package com.connect.core;

/**
 * Created by sven on 8/26/15.
 */

import com.connect.TemplateApplicationImpl;
import com.connect.activities.MainActivity;
import com.connect.screens.DrawerScreen;
import com.connect.util.GsonParceler;
import com.connect.util.RetrofitErrorHandler;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.StateParceler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module(
    complete = false,
    library = true,
    injects = {
        TemplateApplicationImpl.class,
        MainActivity.class
    }
)
public class RootModule {
    //////////////////////////////////////////////////////////////
    //////////////////////// Otto ////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    public Bus provideBus() {
        // Event bus running on any thread - ThreadEnforcer.MAIN is default
        return new Bus(ThreadEnforcer.ANY);
    }

    @Provides
    @Singleton
    StateParceler provideParcer(Gson gson) {
        return new GsonParceler(gson);
    }

    //////////////////////////////////////////////////////////////
    ////////////////////// Retrofit //////////////////////////////
    //////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                //                request.addHeader(
                //                        Constants.HEADER_PLATFORM,
                //                        Constants.PLATFORM_ANDROID
                //                );
                //                request.addHeader(
                //                        Constants.HEADER_VERSION,
                //                        BuildConfig.API_VERSION
                //                );
                //                request.addHeader(
                //                        Constants.HEADER_APP_VERSION,
                //                        BuildConfig.VERSION_NAME
                //                );
            }
        };
    }

    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);
        return client;
    }

    @Provides
    @Singleton
    RestAdapter provideConnectAPIRestAdapter(
        RequestInterceptor requestInterceptor,
        OkHttpClient client,
        Gson gson
    ) {
        return new RestAdapter.Builder().setRequestInterceptor(requestInterceptor)
            //                                        .setEndpoint(BuildConfig.CONNECT_ENDPOINT)
            .setClient(new OkClient(client))
            .setConverter(new GsonConverter(gson))
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setErrorHandler(new RetrofitErrorHandler())
            .build();
    }
}
