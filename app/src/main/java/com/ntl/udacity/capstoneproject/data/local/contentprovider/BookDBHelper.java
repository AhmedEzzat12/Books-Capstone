package com.ntl.udacity.capstoneproject.data.local.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ntl.udacity.capstoneproject.data.local.contentprovider.BookContract.BookEntry.TABLE_NAME;


public class BookDBHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "booksDb.db";

    private static final int DATABASE_VERSION = 1;


    public BookDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                BookContract.BookEntry.COLUMN_TITLE + " TEXT, " +
                BookContract.BookEntry.COLUMN_AUTHOR + " TEXT, " +
                BookContract.BookEntry.COLUMN_DESCRIPTION + " TEXT, " +
                BookContract.BookEntry.COLUMN_API_ID + " TEXT PRIMARY KEY," +
                BookContract.BookEntry.COLUMN_INFORLINK + " TEXT, " +
                BookContract.BookEntry.COLUMN_PUBLISHEDDATE + " TEXT, " +
                BookContract.BookEntry.COLUMN_RATE + " TEXT, " +
                BookContract.BookEntry.COLUMN_IMAGE + " BLOB);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME);
        onCreate(db);

    }
}
