package com.ntl.udacity.capstoneproject.data;

import android.support.annotation.NonNull;

import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;

import static com.google.common.base.Preconditions.checkNotNull;


public class BooksRepository implements BooksDataSource
{

    private static BooksRepository INSTANCE = null;

    private final BooksDataSource mBooksDataSource;
    private final SharedPrefHelper mSharedPrefHelper;

    // Prevent direct instantiation.
    private BooksRepository(@NonNull BooksDataSource booksDataSource, SharedPrefHelper sharedPrefHelper)
    {
        mBooksDataSource = checkNotNull(booksDataSource);
        mSharedPrefHelper = checkNotNull(sharedPrefHelper);
    }

    public static BooksRepository getInstance(BooksDataSource booksRemoteDataSource, SharedPrefHelper sharedPrefHelper)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BooksRepository(booksRemoteDataSource, sharedPrefHelper);
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

}
