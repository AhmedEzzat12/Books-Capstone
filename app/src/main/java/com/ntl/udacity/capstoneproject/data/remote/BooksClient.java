package com.ntl.udacity.capstoneproject.data.remote;

import android.content.Context;

import com.ntl.udacity.capstoneproject.data.BooksRepository;
import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BooksClient
{
    private static final String BASE_URL = "https://www.googleapis.com/";
    private static Retrofit retrofit = null;

    public static BooksInterface getClient(Context context)
    {
        if (retrofit == null)
        {
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(new BooksInterceptor(SharedPrefHelper
                    .getInstance(context)
                    .getSharedPreferenceAccesstoken()));

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(BooksInterface.class);
    }
}
