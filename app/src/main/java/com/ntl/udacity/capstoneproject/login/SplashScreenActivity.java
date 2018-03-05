package com.ntl.udacity.capstoneproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;
import com.ntl.udacity.capstoneproject.data.model.AccessToken;
import com.ntl.udacity.capstoneproject.data.remote.BooksClient;
import com.ntl.udacity.capstoneproject.data.remote.BooksInterface;
import com.ntl.udacity.capstoneproject.home.HomeActivity;
import com.ntl.udacity.capstoneproject.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.ntl.udacity.capstoneproject.util.ClientInfo.CLIENT_ID;
import static com.ntl.udacity.capstoneproject.util.ClientInfo.GRANT_TYPE_AUTHORIZATION_CODE;

public class SplashScreenActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String refreshAccesstoken = SharedPrefHelper.getInstance(this).getSharedPreferenceRefreshAccesstoken();
        Timber.d("refresh accesstoken %s",refreshAccesstoken);
        if (!TextUtils.isEmpty(refreshAccesstoken))
        {
            if (Utility.isNetworkAvailable(this))
            {

                BooksInterface booksInterface = BooksClient.getClient(this);
                final Call<AccessToken> accessToken = booksInterface.refreshAccessToken(refreshAccesstoken,CLIENT_ID, GRANT_TYPE_AUTHORIZATION_CODE);
                accessToken.enqueue(new Callback<AccessToken>()
                {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response)
                    {
                        if (response.body() != null)
                        {
                            String accessTokenStr = response.body().getAccessToken();
                            SharedPrefHelper.getInstance(SplashScreenActivity.this).setSharedPreferenceAccesstoken(accessTokenStr);
                            Timber.d("Splashscreen activity accessToken: %s", accessTokenStr);
                            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }

                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t)
                    {
                        Timber.e(t);
                    }
                });
            } else
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else
        {
            Toast.makeText(this, "please connect to internet", Toast.LENGTH_LONG).show();
        }
    }
}
