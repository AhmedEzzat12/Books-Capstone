package com.ntl.udacity.capstoneproject.login;


import com.ntl.udacity.capstoneproject.data.BooksDataSource;
import com.ntl.udacity.capstoneproject.data.BooksRepository;

public class LoginPresenter implements LoginContract.Presenter
{
    private final LoginContract.View mLoginView;
    private final BooksRepository mBooksRepository;
    public LoginPresenter(LoginContract.View mLoginView, BooksRepository mBooksRepository)
    {
        this.mLoginView = mLoginView;
        this.mBooksRepository = mBooksRepository;
        mLoginView.setPresenter(this);
    }

    @Override
    public void start()
    {

    }


    @Override
    public void getTokenFromUrl(String code, String clientId, String redirectUri, String grantType)
    {
        mBooksRepository.getAccesstoken(new BooksDataSource.GetAccesstokenCallback()
        {
            @Override
            public void onTokenLoaded(String acesstoken, String refreshAccesstoken)
            {
                //handle view here
                mLoginView.showSuccessfullAuthentication();
            }

            @Override
            public void onThereIsError(Throwable t)
            {
                //handle view here
                mLoginView.showFailedAuthentication();
            }
        }, code, clientId, redirectUri, grantType);
    }
}
