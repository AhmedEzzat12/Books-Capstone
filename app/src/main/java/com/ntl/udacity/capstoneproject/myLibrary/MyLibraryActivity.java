package com.ntl.udacity.capstoneproject.myLibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.util.Inject;

import static com.ntl.udacity.capstoneproject.util.ActivityUtils.addFragmentToActivity;
import static com.ntl.udacity.capstoneproject.util.ActivityUtils.showFragment;

public class MyLibraryActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);

        Toolbar toolbar = findViewById(R.id.mylibrary_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Library");
        FragmentManager fragmentManager = getSupportFragmentManager();

        MylibraryFragment mylibraryFragment = (MylibraryFragment) fragmentManager.findFragmentById(R.id.contentFrame);
        if (mylibraryFragment == null)
        {
            mylibraryFragment = MylibraryFragment.getInstance();
            addFragmentToActivity(fragmentManager, mylibraryFragment, R.id.contentFrame);

        } else
        {
            showFragment(fragmentManager, mylibraryFragment);
        }
        new MyLibraryPresenter(mylibraryFragment, Inject.provideBooksRepository(this));
    }
}
