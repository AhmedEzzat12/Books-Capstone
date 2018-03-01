package com.ntl.udacity.capstoneproject.home;


import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.BooksRepository;
import com.ntl.udacity.capstoneproject.data.model.BookItem;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter
{
    private final HomeContract.View mHomeView;
    private final BooksRepository mBooksRepository;

    public HomePresenter(HomeContract.View mHomeView, BooksRepository mBooksRepository)
    {
        this.mHomeView = mHomeView;
        this.mBooksRepository = mBooksRepository;
        mHomeView.setPresenter(this);
    }

    @Override
    public void start()
    {

    }

    @Override
    public void getBooks(String query)
    {
        mBooksRepository.searchSpecificBook(new BooksDataSource.getSearchBookCallback()
        {
            @Override
            public void onBooksListLoaded(List<BookItem> bookItemsList)
            {
                mHomeView.booksAreReady(bookItemsList);
            }

            @Override
            public void onThereIsError()
            {
                mHomeView.cantLoadBooks();
            }
        }, query);
    }
}
