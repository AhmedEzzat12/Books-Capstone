package com.ntl.udacity.capstoneproject.home;


import com.ntl.udacity.capstoneproject.BasePresenter;
import com.ntl.udacity.capstoneproject.BaseView;
import com.ntl.udacity.capstoneproject.data.model.BookItem;

import java.util.List;

public interface HomeContract
{
    interface View extends BaseView<Presenter>
    {
        void booksAreReady(List<BookItem> bookItemList);

        void cantLoadBooks();
    }

    interface Presenter extends BasePresenter
    {
        void getBooks(String query);
    }
}
