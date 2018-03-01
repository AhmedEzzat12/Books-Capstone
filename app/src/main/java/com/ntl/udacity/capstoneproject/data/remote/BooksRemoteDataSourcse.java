package com.ntl.udacity.capstoneproject.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.model.AccessToken;
import com.ntl.udacity.capstoneproject.data.model.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;


public class BooksRemoteDataSourcse implements BooksDataSource
{
    private static BooksRemoteDataSourcse INSTANCE;
    private Context context;

    public BooksRemoteDataSourcse(Context context)
    {
        this.context = context;
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
    public void getAccesstoken(final getAccesstokenCallback callback, String code, String clientId, String redirectUri, String grantType)
    {
        BooksInterface booksInterface = BooksClient.getClient(context);
        Call<AccessToken> requestAccessToken = booksInterface.requestAccessToken(code, clientId, redirectUri, grantType);
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
    public void searchSpecificBook(final getSearchBookCallback callback, String query)
    {
        BooksInterface booksInterface = BooksClient.getClient(context);
        Call<SearchResponse> requestBooks = booksInterface.searchVolumes(query);
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

}
