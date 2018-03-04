package com.ntl.udacity.capstoneproject.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.model.AccessToken;
import com.ntl.udacity.capstoneproject.data.model.BookShelfResponse;
import com.ntl.udacity.capstoneproject.data.model.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;


public class BooksRemoteDataSourcse implements BooksDataSource
{
    private static BooksRemoteDataSourcse INSTANCE;
    private BooksInterface mBooksInterface;

    public BooksRemoteDataSourcse(Context context)
    {
        mBooksInterface = BooksClient.getClient(context);
    }

    public static BooksRemoteDataSourcse getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BooksRemoteDataSourcse(context);
        }
        return INSTANCE;
    }


    @Override
    public void getAccesstoken(final GetAccesstokenCallback callback, String code, String clientId, String redirectUri, String grantType)
    {
        Call<AccessToken> requestAccessToken = mBooksInterface.requestAccessToken(code, clientId, redirectUri, grantType);
        requestAccessToken.enqueue(new Callback<AccessToken>()
        {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response)
            {
                if (response.body() != null)
                {
                    callback.onTokenLoaded(response.body().getAccessToken(), response.body().getRefreshToken());
                } else
                {
                    Timber.e(response.message());

                    callback.onThereIsError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError();
            }
        });

    }

    @Override
    public void searchSpecificBook(final GetSearchBookCallback callback, String query)
    {
        Call<SearchResponse> requestBooks = mBooksInterface.searchVolumes(query);
        requestBooks.enqueue(new Callback<SearchResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response)
            {
                try
                {
                    checkNotNull(response.body());
                    checkNotNull(response.body().getItems());
                    callback.onBooksListLoaded(response.body().getItems());
                } catch (NullPointerException e)
                {
                    callback.onThereIsError();
                    Timber.e(e);
                    Timber.e("response code is %s", response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError();
            }
        });
    }

    @Override
    public void getUserbookshelves(final UserBookshelvesCallback callback)
    {
        //TODO handle Offline aka Contentprovider
        Call<BookShelfResponse> bookShelfResponseCall = mBooksInterface.getUserBookshelves();
        bookShelfResponseCall.enqueue(new Callback<BookShelfResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<BookShelfResponse> call, @NonNull Response<BookShelfResponse> response)
            {
                if (response.body() != null)
                {
                    callback.onBookshelvesLoaded(response.body().getItems());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookShelfResponse> call, @NonNull Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError();
            }
        });
    }

}
