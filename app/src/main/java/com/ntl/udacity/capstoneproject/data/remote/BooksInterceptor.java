package com.ntl.udacity.capstoneproject.data.remote;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BooksInterceptor implements Interceptor
{
    private static final String NO_AUTH_HEADER_KEY = "No-Authentication";
    private String authToken;

    public BooksInterceptor(String authToken)
    {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException
    {
        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder();

        if (request.header(NO_AUTH_HEADER_KEY)== null)
        {
            if (authToken == null)
            {
                throw new RuntimeException("Accesstoken should be defined");
            } else
            {
                requestBuilder.addHeader("Authorization", "Bearer " + authToken);
            }
        }

        return chain.proceed(requestBuilder.build());
    }
}
