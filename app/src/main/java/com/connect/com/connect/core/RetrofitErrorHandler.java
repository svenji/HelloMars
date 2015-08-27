package com.connect.com.connect.core;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by sven on 8/26/15.
 */
public class RetrofitErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        return cause;
    }
}
