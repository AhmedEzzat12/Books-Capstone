package com.ntl.udacity.capstoneproject.bookDetail;


import com.ntl.udacity.capstoneproject.data.BooksRepository;

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
}
