package com.ntl.udacity.capstoneproject.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BooksInterceptor implements Interceptor
{
    private static final String NO_AUTH_HEADER_KEY = "No-Authentication";
    private final Context mContext;

    public BooksInterceptor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException
    {
        String authToken = SharedPrefHelper
                .getInstance(mContext)
                .getSharedPreferenceAccesstoken();

        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder();

        if (request.header(NO_AUTH_HEADER_KEY) == null)
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
