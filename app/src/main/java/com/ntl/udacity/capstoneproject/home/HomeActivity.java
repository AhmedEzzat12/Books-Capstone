package com.ntl.udacity.capstoneproject.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.home.plus.EditorActionOnClick;
import com.ntl.udacity.capstoneproject.home.plus.OnEditorActionListenerDone;
import com.ntl.udacity.capstoneproject.myLibrary.MyLibraryActivity;
import com.ntl.udacity.capstoneproject.util.Inject;

import static com.ntl.udacity.capstoneproject.util.ActivityUtils.addFragmentToActivity;
import static com.ntl.udacity.capstoneproject.util.ActivityUtils.showFragment;

public class HomeActivity extends AppCompatActivity implements EditorActionOnClick
{
    private EditText search;
    private HomeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        search = toolbar.findViewById(R.id.edittext_home_search);
        search.setOnEditorActionListener(new OnEditorActionListenerDone(this));
        int contentID = R.id.contentFrame;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = (HomeFragment) fragmentManager.findFragmentById(contentID);
        if (fragment == null)
        {
            fragment = HomeFragment.getInstance();
            addFragmentToActivity(fragmentManager, fragment, contentID);
        } else
        {
            showFragment(fragmentManager, fragment);
        }

        new HomePresenter(fragment, Inject.provideBooksRepository(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.my_library:
                Intent intent = new Intent(HomeActivity.this, MyLibraryActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                //TODO handle logout
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getBooks(String query)
    {
        search.clearFocus();
        fragment.getBooks(query);
    }
}
