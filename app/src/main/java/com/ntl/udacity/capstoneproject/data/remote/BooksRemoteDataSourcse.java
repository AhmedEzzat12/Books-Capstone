package com.ntl.udacity.capstoneproject.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;
import com.ntl.udacity.capstoneproject.data.model.AccessToken;
import com.ntl.udacity.capstoneproject.data.model.BookShelfResponse;
import com.ntl.udacity.capstoneproject.data.model.KindBooksVolume;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;


public class BooksRemoteDataSourcse implements BooksDataSource
{
    private static BooksRemoteDataSourcse INSTANCE;
    private BooksInterface mBooksInterface;
    private Context mContext;

    private BooksRemoteDataSourcse(Context context)
    {
        mBooksInterface = BooksClient.getClient(context);
        this.mContext = context;
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
                    SharedPrefHelper.getInstance(mContext).setSharedPreferenceAccesstoken(response.body().getAccessToken());
                    SharedPrefHelper.getInstance(mContext).setSharedPreferenceRefreshAccesstoken(response.body().getRefreshToken());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError(t);
            }
        });

    }

    @Override
    public void searchSpecificBook(final GetSearchBookCallback callback, String query)
    {
        Call<KindBooksVolume> requestBooks = mBooksInterface.searchVolumes(query);
        requestBooks.enqueue(new Callback<KindBooksVolume>()
        {
            @Override
            public void onResponse(@NonNull Call<KindBooksVolume> call, @NonNull Response<KindBooksVolume> response)
            {
                try
                {
                    checkNotNull(response.body());
                    checkNotNull(response.body().getItems());
                    callback.onBooksListLoaded(response.body().getItems());
                } catch (NullPointerException e)
                {
                    callback.onThereIsError(e);
                    Timber.e(e);
                    Timber.e("response code is %s", response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<KindBooksVolume> call, @NonNull Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError(t);
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
                callback.onThereIsError(t);
            }
        });
    }

    @Override
    public void getUserbookshelfContent(final GetBookshelfContents callback, String bookshelfId)
    {
        Call<KindBooksVolume> booksVolumeCall = mBooksInterface.getlistOfVolumesFromBookshelf(bookshelfId);
        booksVolumeCall.enqueue(new Callback<KindBooksVolume>()
        {
            @Override
            public void onResponse(Call<KindBooksVolume> call, Response<KindBooksVolume> response)
            {
                if (response.body() != null)
                {
                    callback.onBooksListLoaded(response.body().getItems());
                }

            }

            @Override
            public void onFailure(Call<KindBooksVolume> call, Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError(t);
            }
        });

    }

    @Override
    public void removeBookFromBookshelf(final RemoveBookFromBookshelfCallBack callback, String bookshelfId, String bookId)
    {
        Call<EmptyResponse> booksVolumeCall = mBooksInterface.removeVolumeFromBookshelf(bookshelfId, bookId);
        booksVolumeCall.enqueue(new Callback<EmptyResponse>()
        {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response)
            {
                if (response.code() == 204)
                {
                    callback.onBookIsRemoved();
                }
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t)
            {
                Timber.e(t);
                callback.onThereIsError(t);

            }
        });

    }

    @Override
    public void clearBookshelf(final ClearBookshelfCallback callback, String bookshelfId)
    {
        Call<EmptyResponse> booksVolumeCall = mBooksInterface.clearVolumesFromBookshelf(bookshelfId);
        booksVolumeCall.enqueue(new Callback<EmptyResponse>()
        {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response)
            {

                callback.onBookshelfCleared();

            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t)
            {
                callback.onThereIsError(t);
            }
        });

    }

}
