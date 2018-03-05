package com.ntl.udacity.capstoneproject.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.updatedatajob.MyJobService;

public class UpdateWidgetData extends IntentService
{
    private static final String ACTION_UPDATE_DATA = "com.ntl.udacity.capstoneproject.widget.action.update";

    public UpdateWidgetData()
    {
        super("UpdateWidgetData");
    }

    public static void startActionUpdateData(Context context)
    {
        Intent intent = new Intent(context, UpdateWidgetData.class);
        intent.setAction(ACTION_UPDATE_DATA);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            final String action = intent.getAction();
            if (ACTION_UPDATE_DATA.equals(action))
            {
                handleActionUpdateData();
            }
        }
    }

    private void handleActionUpdateData()
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BookWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_view);
        BookWidget.updateAppWidget(this, appWidgetManager, appWidgetIds);
        Intent updateFinishedIntent = new Intent(MyJobService.ACTION_UPDATE_FINISHED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateFinishedIntent);
    }
}
