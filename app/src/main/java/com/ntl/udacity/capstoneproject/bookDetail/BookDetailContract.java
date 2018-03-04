package com.ntl.udacity.capstoneproject.bookDetail;


import com.ntl.udacity.capstoneproject.BasePresenter;
import com.ntl.udacity.capstoneproject.BaseView;

public interface BookDetailContract
{
    interface View extends BaseView<Presenter>
    {

        void showBookDetail();
    }

    interface Presenter extends BasePresenter
    {

    }
}
