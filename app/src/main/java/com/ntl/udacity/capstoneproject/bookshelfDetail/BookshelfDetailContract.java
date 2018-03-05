package com.ntl.udacity.capstoneproject.bookshelfDetail;


import com.ntl.udacity.capstoneproject.BasePresenter;
import com.ntl.udacity.capstoneproject.BaseView;
import com.ntl.udacity.capstoneproject.data.model.BookItem;

import java.util.List;

public interface BookshelfDetailContract
{
    interface View extends BaseView<Presenter>
    {
        void onBooksLoaded(List<BookItem> bookItemList);

        void onBookRemoved();

        void onBookClicked(int adapterPosition);

        void clearBooks();

        void removeBook(String id);

        void onThereAreNoBooks();

        void onBookshelfCleared();
    }

    interface Presenter extends BasePresenter
    {
        void getBooks(String bookshelfId);

        void removeBook(String bookshelfId, String bookId);

        void clearBookshelf(String bookshelfId);
    }
}
