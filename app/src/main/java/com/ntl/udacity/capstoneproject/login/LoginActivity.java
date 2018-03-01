package com.ntl.udacity.capstoneproject.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.home.HomeActivity;
import com.ntl.udacity.capstoneproject.util.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements LoginContract.View
{
    private static final String BASE_URI = "https://accounts.google.com/o/oauth2/v2/auth?";
    private static final String API_SCOPE = "https://www.googleapis.com/auth/books";
    private static final String CODE = "code";
    private static final String ERROR_CODE = "error";
    private static final String REDIRECT_URI_ROOT = "com.ntl.udacity.capstoneproject";
    private static final String REDIRECT_URI = "com.ntl.udacity.capstoneproject:/oauth2redirect";
    private static final String CLIENT_ID = "925179029705-h5mn3n5kth8f99psha99hn40muisnt43.apps.googleusercontent.com";
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, Inject.provideBooksRepository(getApplicationContext()));
        Timber.tag("LifeCycles");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Uri data = getIntent().getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme()))
        {
            if (REDIRECT_URI_ROOT.equals(data.getScheme()))
            {
                String mCode = data.getQueryParameter(CODE);
                String mError = data.getQueryParameter(ERROR_CODE);
                if (!TextUtils.isEmpty(mCode))
                {
                    mPresenter.getTokenFromUrl(mCode, CLIENT_ID, REDIRECT_URI, GRANT_TYPE_AUTHORIZATION_CODE);
                }
                if (!TextUtils.isEmpty(mError))
                {
                    showErrorDialog();
                    Timber.e("onCreate: handle result of authorization with error :%s", mError);
                }
            }
        }
    }

    @OnClick(R.id.login_button)
    public void loginToAuthorize()
    {
        Uri builder = Uri.parse(BASE_URI)
                .buildUpon()
                .appendQueryParameter("scope", API_SCOPE)
                .appendQueryParameter("response_type", CODE)
                .appendQueryParameter("redirect_uri", REDIRECT_URI)
                .appendQueryParameter("client_id", CLIENT_ID)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW, builder);
        startActivity(intent);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessfullAuthentication()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void showFailedAuthentication()
    {
        showErrorDialog();
    }

    public void showErrorDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("failed");
        builder.setMessage("something happend during your login process please try again");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                loginToAuthorize();
            }
        });
        builder.show();

    }
}
