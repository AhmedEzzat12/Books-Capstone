package com.ntl.udacity.capstoneproject.bookshelfDetail;


public class BookshelfDetailPresenter implements BbookshelfDetailContract.Presenter
{
    private final BbookshelfDetailContract.View mBookshelfView;

    public BookshelfDetailPresenter(BbookshelfDetailContract.View mBookshelfView)
    {
        this.mBookshelfView = mBookshelfView;
    }

    @Override
    public void start()
    {

    }
}
