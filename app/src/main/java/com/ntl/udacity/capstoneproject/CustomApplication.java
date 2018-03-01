package com.ntl.udacity.capstoneproject;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by ahmed on 2/26/2018.
 */

public class CustomApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //TODO handle release Build
            //Timber.plant(new CrashReportingTree());
        }

    }
}
