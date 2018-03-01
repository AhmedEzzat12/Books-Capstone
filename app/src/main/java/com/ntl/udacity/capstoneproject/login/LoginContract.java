package com.ntl.udacity.capstoneproject.login;


import com.ntl.udacity.capstoneproject.BasePresenter;
import com.ntl.udacity.capstoneproject.BaseView;

public interface LoginContract
{
    interface View extends BaseView<Presenter>
    {
        void showSuccessfullAuthentication();
        void showFailedAuthentication();
    }

    interface Presenter extends BasePresenter
    {
        void getTokenFromUrl(String code,String clientId,String redirectUri,String grantType);
    }
}
