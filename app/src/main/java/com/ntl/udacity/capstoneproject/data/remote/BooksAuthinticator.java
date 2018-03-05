package com.ntl.udacity.capstoneproject.data.remote;

import android.content.Context;

import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;
import com.ntl.udacity.capstoneproject.data.model.AccessToken;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import timber.log.Timber;

import static com.ntl.udacity.capstoneproject.util.ClientInfo.CLIENT_ID;
import static com.ntl.udacity.capstoneproject.util.ClientInfo.GRANT_TYPE_AUTHORIZATION_CODE;


public class BooksAuthinticator implements Authenticator
{
    private Context mContext;

    public BooksAuthinticator(Context mContext)
    {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException
    {
        if (response.request().header("Authorization") != null)
        {
            return null; // Give up, we've already attempted to authenticate.
        }

        String refreshAccesstoken = SharedPrefHelper.getInstance(mContext).getSharedPreferenceRefreshAccesstoken();

        Timber.d("Authenticating for response: %s", response);
        Timber.d("Challenges: %s", response.challenges());

        Call<AccessToken> accessTokenCall = BooksClient.getClient(mContext).refreshAccessToken(refreshAccesstoken
                , CLIENT_ID
                , GRANT_TYPE_AUTHORIZATION_CODE);

        String accessToken = "";
        try
        {
            accessToken = accessTokenCall.execute().body().getAccessToken();
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

        } catch (NullPointerException e)
        {
            Timber.e(e);
            return null;
        }


    }
}
