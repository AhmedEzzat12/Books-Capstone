package com.ntl.udacity.capstoneproject.bookDetail;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.google.common.base.MoreObjects;
import com.ntl.udacity.capstoneproject.data.BooksRepository;
import com.ntl.udacity.capstoneproject.data.local.contentprovider.BookContract;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.util.Utility;

import timber.log.Timber;

public class BookDetailPresenter implements BookDetailContract.Presenter
{
    private final BookDetailContract.View mBookDetailView;
    private final BooksRepository mBooksRepository;

    public BookDetailPresenter(BookDetailContract.View mBookDetailView, BooksRepository mBooksRepository)
    {
        this.mBookDetailView = mBookDetailView;
        this.mBooksRepository = mBooksRepository;
        this.mBookDetailView.setPresenter(this);
    }

    @Override
    public void start()
    {
        mBookDetailView.showBookDetail();
    }

    @Override
    public void addBookToFavourite(BookItem bookItem, Bitmap bitmap, Context context)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookContract.BookEntry.COLUMN_API_ID, bookItem.getId());
        contentValues.put(BookContract.BookEntry.COLUMN_TITLE, bookItem.getVolumeInfo().getTitle());
        contentValues.put(BookContract.BookEntry.COLUMN_RATE, bookItem.getVolumeInfo().getAverageRating());
        contentValues.put(BookContract.BookEntry.COLUMN_AUTHOR, MoreObjects.firstNonNull(bookItem.getVolumeInfo().getAuthors().get(0), ""));
        contentValues.put(BookContract.BookEntry.COLUMN_PUBLISHEDDATE, bookItem.getVolumeInfo().getPublishedDate());
        contentValues.put(BookContract.BookEntry.COLUMN_IMAGE, Utility.getImageBytes(bitmap));
        contentValues.put(BookContract.BookEntry.COLUMN_DESCRIPTION, bookItem.getVolumeInfo().getDescription());
        contentValues.put(BookContract.BookEntry.COLUMN_INFORLINK, bookItem.getVolumeInfo().getInfoLink());

        Uri uri = BookContract.BookEntry.CONTENT_URI.buildUpon().appendPath(bookItem.getId()).build();
        try
        {
            Uri resUri = context.getContentResolver().insert(uri, contentValues);
            if (resUri != null)
            {
                mBookDetailView.onBookAddedToFavourite(resUri);
            }
        } catch (Exception e)
        {
            Timber.e(e);
            mBookDetailView.onBookFailAddToFavourite();
        }

    }

    @Override
    public void removeBookFromFavourite(String bookId, Context context)
    {
        Uri uri = BookContract.BookEntry.CONTENT_URI.buildUpon().appendPath(bookId).build();
        try
        {
            int deleted = context.getContentResolver().delete(uri, null, null);
            if (deleted > 0)
            {
                mBookDetailView.onBookRemovedFromFavourite();
            }

        } catch (Exception e)
        {
            Timber.e(e);
            mBookDetailView.onFailRemoveFromFavourite();
        }

    }
}
