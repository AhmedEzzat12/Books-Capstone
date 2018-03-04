package com.ntl.udacity.capstoneproject.myLibrary;


import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.BooksRepository;
import com.ntl.udacity.capstoneproject.data.model.BookShelfItem;

import java.util.List;

public class MyLibraryPresenter implements MyLibraryContract.Presenter
{
    private final BooksRepository mBooksRepository;
    private final MyLibraryContract.View mMyLibraryView;

    public MyLibraryPresenter(MyLibraryContract.View mMyLibraryView, BooksRepository booksRepository)
    {
        this.mMyLibraryView = mMyLibraryView;
        this.mBooksRepository = booksRepository;
        mMyLibraryView.setPresenter(this);
    }

    @Override
    public void start()
    {

    }

    @Override
    public void loodBookshelves()
    {
        mBooksRepository.getUserbookshelves(new BooksDataSource.UserBookshelvesCallback()
        {
            @Override
            public void onBookshelvesLoaded(List<BookShelfItem> bookShelfItems)
            {
                mMyLibraryView.BookshelvesLoaded(bookShelfItems);
            }

            @Override
            public void onThereIsError()
            {

            }
        });
    }
}
