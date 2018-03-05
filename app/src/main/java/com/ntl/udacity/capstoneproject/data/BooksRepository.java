package com.ntl.udacity.capstoneproject.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;


public class BooksRepository implements BooksDataSource
{

    private static BooksRepository INSTANCE = null;

    private final BooksDataSource mBooksDataSource;

    private BooksRepository(@NonNull BooksDataSource booksDataSource)
    {
        mBooksDataSource = checkNotNull(booksDataSource);
    }

    public static BooksRepository getInstance(BooksDataSource booksRemoteDataSource)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BooksRepository(booksRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }

    @Override
    public void getAccesstoken(GetAccesstokenCallback callback, String code, String clientId, String redirectUri, String grantType)
    {
        mBooksDataSource.getAccesstoken(callback,code,clientId,redirectUri,grantType);
    }

    @Override
    public void searchSpecificBook(GetSearchBookCallback callback, String query)
    {
        mBooksDataSource.searchSpecificBook(callback,query);
    }

    @Override
    public void getUserbookshelves(UserBookshelvesCallback callback)
    {
        mBooksDataSource.getUserbookshelves(callback);
    }

    @Override
    public void getUserbookshelfContent(GetBookshelfContents callback, String bookshelfId)
    {
        mBooksDataSource.getUserbookshelfContent(callback, bookshelfId);

    }

    @Override
    public void removeBookFromBookshelf(RemoveBookFromBookshelfCallBack callback, String bookshelfId, String bookId)
    {
        mBooksDataSource.removeBookFromBookshelf(callback, bookshelfId, bookId);
    }

    @Override
    public void clearBookshelf(ClearBookshelfCallback callback, String bookshelfId)
    {
        mBooksDataSource.clearBookshelf(callback, bookshelfId);
    }


}
