package com.ntl.udacity.capstoneproject.myLibrary;


public class MyLibraryPresenter implements MyLibraryContract.Presenter
{
    private final MyLibraryContract.View mMyLibraryView;

    public MyLibraryPresenter(MyLibraryContract.View mMyLibraryView)
    {
        this.mMyLibraryView = mMyLibraryView;
    }

    @Override
    public void start()
    {

    }
}
