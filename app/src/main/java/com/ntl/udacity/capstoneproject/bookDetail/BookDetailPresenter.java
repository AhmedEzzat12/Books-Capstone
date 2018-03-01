package com.ntl.udacity.capstoneproject.bookDetail;


public class BookDetailPresenter implements BookDetailContract.Presenter
{
    private final BookDetailContract.View mBookDetailView;

    public BookDetailPresenter(BookDetailContract.View mBookDetailView)
    {
        this.mBookDetailView = mBookDetailView;
    }

    @Override
    public void start()
    {

    }
}
