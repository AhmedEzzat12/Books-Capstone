package com.ntl.udacity.capstoneproject.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ntl.udacity.capstoneproject.data.local.contentprovider.BookContract;


public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context mContext;
    private Cursor mCursor;

    public ListRemoteViewsFactory(Context applicationContext)
    {
        this.mContext = applicationContext;
    }

    @Override
    public void onCreate()
    {
    }

    @Override
    public void onDataSetChanged()
    {
        Uri uri = BookContract.BookEntry.CONTENT_URI;
        if (mCursor != null)
            mCursor.close();
        mCursor = mContext.getContentResolver().query(
                uri,
                new String[]{BookContract.BookEntry.COLUMN_TITLE},
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy()
    {
        mCursor.close();
    }

    @Override
    public int getCount()
    {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();

    }

    @Override
    public RemoteViews getViewAt(int position)
    {
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
}
