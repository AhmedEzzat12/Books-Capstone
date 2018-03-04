package com.ntl.udacity.capstoneproject.myLibrary;


import com.ntl.udacity.capstoneproject.BasePresenter;
import com.ntl.udacity.capstoneproject.BaseView;
import com.ntl.udacity.capstoneproject.data.model.BookShelfItem;

import java.util.List;

public interface MyLibraryContract
{
    interface View extends BaseView<Presenter>
    {
        void BookshelvesLoaded(List<BookShelfItem> bookShelfItems);

        void onBookshelfClicked(int adapterPosition);
    }

    interface Presenter extends BasePresenter
    {
        void loodBookshelves();
    }
}
