package com.ntl.udacity.capstoneproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.ntl.udacity.capstoneproject.updatedatajob.MyJobService;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;


public class Utility
{
    private static final int INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(1));
    private static final int SYNC_SECONDS = INTERVAL_SECONDS;
    private static final String JOB_TAG = "update_data_tag";
    private static boolean sInitialized;

    public static boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static byte[] getImageBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    synchronized public static void scheduleChargingReminder(@NonNull final Context context)
    {
        if (sInitialized)
            return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag(JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        INTERVAL_SECONDS,
                        INTERVAL_SECONDS + SYNC_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;
    }

}
