package com.ntl.udacity.capstoneproject.bookshelfDetail;


import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.BooksRepository;
import com.ntl.udacity.capstoneproject.data.model.BookItem;

import java.util.List;

public class BookshelfDetailPresenter implements BookshelfDetailContract.Presenter
{
    private final BookshelfDetailContract.View mBookshelfView;
    private final BooksRepository mBookRepository;


    public BookshelfDetailPresenter(BookshelfDetailContract.View mBookshelfView1, BooksRepository booksRepository)
    {
        this.mBookshelfView = mBookshelfView1;
        this.mBookRepository = booksRepository;
        mBookshelfView.setPresenter(this);
    }

    @Override
    public void start()
    {
    }

    @Override
    public void getBooks(String bookshelfId)
    {
        mBookRepository.getUserbookshelfContent(new BooksDataSource.GetBookshelfContents()
        {
            @Override
            public void onBooksListLoaded(List<BookItem> bookItemsList)
            {
                mBookshelfView.onBooksLoaded(bookItemsList);
            }

            @Override
            public void onThereIsError(Throwable t)
            {
                mBookshelfView.onThereAreNoBooks();
            }
        }, bookshelfId);
    }

    @Override
    public void removeBook(String bookshelfId, String bookId)
    {
        mBookRepository.removeBookFromBookshelf(new BooksDataSource.RemoveBookFromBookshelfCallBack()
        {
            @Override
            public void onBookIsRemoved()
            {
                mBookshelfView.onBookRemoved();
            }

            @Override
            public void onThereIsError(Throwable t)
            {

            }
        }, bookshelfId, bookId);
    }

    @Override
    public void clearBookshelf(String bookshelfId)
    {
        mBookRepository.clearBookshelf(new BooksDataSource.ClearBookshelfCallback()
        {
            @Override
            public void onBookshelfCleared()
            {
                mBookshelfView.onBookshelfCleared();
            }

            @Override
            public void onThereIsError(Throwable t)
            {

            }
        }, bookshelfId);
    }


}
