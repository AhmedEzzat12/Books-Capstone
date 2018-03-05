package com.ntl.udacity.capstoneproject.data.local.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;


public class BookContract
{
    public static final String AUTHORITY = "com.ntl.udacity.capstoneproject";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_BOOKS = "books";

    public static final class BookEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOKS).build();


        public static final String TABLE_NAME = "books";
        public static final String COLUMN_API_ID = "api_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_PUBLISHEDDATE = "publishdate";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_INFORLINK = "infolink";
        public static final String COLUMN_IMAGE = "imagecol";


    }
}
