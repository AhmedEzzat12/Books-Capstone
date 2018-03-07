package com.ntl.udacity.capstoneproject.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ntl.udacity.capstoneproject.data.local.contentprovider.BookContract;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, Loader.OnLoadCompleteListener<Cursor>
{
    private static final int LOADER_ID = 100;
    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    private CursorLoader mCursorLoader;

    public ListRemoteViewsFactory(Context applicationContext)
    {
        this.mContext = applicationContext;
    }

    @Override
    public void onCreate()
    {
        mCursorLoader = new CursorLoader(mContext, BookContract.BookEntry.CONTENT_URI
                , new String[]{BookContract.BookEntry.COLUMN_TITLE}, null, null, null);
        mCursorLoader.registerListener(LOADER_ID, this);
        mCursorLoader.startLoading();
        Log.d(TAG, "on Create");
    }

    @Override
    public void onDataSetChanged()
    {
        Log.d(TAG, "onDataSetChanged");
        if (mCursorLoader.isStarted())
            mCursorLoader.forceLoad();
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");
        if (mCursor != null)
        {
            mCursor.close();
        }
        if (mCursorLoader != null)
        {
            mCursorLoader.unregisterListener(this);
            mCursorLoader.cancelLoad();
            mCursorLoader.reset();
        }
    }

    @Override
    public int getCount()
    {
        Log.d(TAG, "getCount");
        if (mCursor == null)
            return 0;
        return mCursor.getCount();

    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        Log.d(TAG, "getViewAt" + String.valueOf(position));
        if (mCursor == null || mCursor.getCount() == 0)
            return null;
        mCursor.moveToPosition(position);
        int titleIndex = mCursor.getColumnIndex(BookContract.BookEntry.COLUMN_TITLE);
        String title = mCursor.getString(titleIndex);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        views.setTextViewText(android.R.id.text1, title);
        return views;

    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }


    @Override
    public void onLoadComplete(@NonNull Loader<Cursor> loader, @Nullable Cursor data)
    {
        Log.d(TAG, "onLoadComplete");
        this.mCursor = data;

    }
}
