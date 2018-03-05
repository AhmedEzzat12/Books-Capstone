package com.ntl.udacity.capstoneproject.updatedatajob;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.firebase.jobdispatcher.JobParameters;
import com.ntl.udacity.capstoneproject.widget.UpdateWidgetData;


public class MyJobService extends com.firebase.jobdispatcher.JobService
{
    public static final String ACTION_UPDATE_FINISHED = "actionUpdateFinished";
    private JobParameters mJobParamters;

    private BroadcastReceiver updateFinishedReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
            if (mJobParamters != null)
            {
                jobFinished(mJobParamters, true);
            }
        }
    };

    @Override
    public boolean onStartJob(JobParameters job)
    {
        this.mJobParamters = job;
        UpdateWidgetData.startActionUpdateData(MyJobService.this);
        IntentFilter filter = new IntentFilter(ACTION_UPDATE_FINISHED);
        //Use LocalBroadcastManager to catch the intents only from your app
        LocalBroadcastManager.getInstance(this).registerReceiver(updateFinishedReceiver, filter);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job)
    {
        return false;
    }
}
