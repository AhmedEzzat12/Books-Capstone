package com.ntl.udacity.capstoneproject.data.local.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class BookContentProvider extends ContentProvider
{
    public static final int BOOK = 100;
    public static final int BOOK_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BookDBHelper mBookDBHelper;

    private static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BookContract.AUTHORITY, BookContract.PATH_BOOKS, BOOK);
        uriMatcher.addURI(BookContract.AUTHORITY, BookContract.PATH_BOOKS + "/*", BOOK_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate()
    {
        mBookDBHelper = new BookDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        final SQLiteDatabase db = mBookDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match)
        {
            case BOOK:
                cursor = db.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                cursor = db.query(BookContract.BookEntry.TABLE_NAME, null
                        , BookContract.BookEntry.COLUMN_API_ID + " = ?", new String[]{id}, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        final SQLiteDatabase db = mBookDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri resulUri;
        switch (match)
        {
            case BOOK_WITH_ID:
                long id = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);
                if (id > 0)
                {
                    resulUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, id);
                } else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return resulUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        final SQLiteDatabase db = mBookDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri), bookDeleted;
        switch (match)
        {
            case BOOK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                bookDeleted = db.delete(BookContract.BookEntry.TABLE_NAME
                        , BookContract.BookEntry.COLUMN_API_ID + " = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException();

        }
        if (bookDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return bookDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        return 0;
    }
}
