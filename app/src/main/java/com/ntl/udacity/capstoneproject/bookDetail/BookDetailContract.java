package com.ntl.udacity.capstoneproject.bookDetail;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.ntl.udacity.capstoneproject.BasePresenter;
import com.ntl.udacity.capstoneproject.BaseView;
import com.ntl.udacity.capstoneproject.data.model.BookItem;

public interface BookDetailContract
{
    interface View extends BaseView<Presenter>
    {

        void showBookDetail();

        void addBookToFavourite();

        void removeBookFromFavourite();

        void chooseBookshelf();

        void onBookAddedToFavourite(Uri uri);

        void onBookFailAddToFavourite();

        void onBookRemovedFromFavourite();

        void onFailRemoveFromFavourite();

    }

    interface Presenter extends BasePresenter
    {
        void addBookToFavourite(BookItem bookItem, Bitmap bitmap, Context context);

        void removeBookFromFavourite(String bookId, Context context);
    }
}
