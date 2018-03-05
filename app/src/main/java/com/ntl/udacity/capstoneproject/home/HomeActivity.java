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
import com.ntl.udacity.capstoneproject.bookDetail.BookDetailFragment;
import com.ntl.udacity.capstoneproject.bookDetail.BookDetailPresenter;
import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.home.plus.EditorActionOnClick;
import com.ntl.udacity.capstoneproject.home.plus.OnEditorActionListenerDone;
import com.ntl.udacity.capstoneproject.myLibrary.MyLibraryActivity;
import com.ntl.udacity.capstoneproject.util.Inject;
import com.ntl.udacity.capstoneproject.util.Utility;

import static com.ntl.udacity.capstoneproject.util.ActivityUtils.addFragmentToActivity;
import static com.ntl.udacity.capstoneproject.util.ActivityUtils.showFragment;

public class HomeActivity extends AppCompatActivity implements EditorActionOnClick, OnBookIsSelected
{
    public static final String TWOPANE = "two_pane";
    private EditText search;
    private HomeFragment mHomefragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        boolean twoPane = findViewById(R.id.master_container) != null;

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        search = toolbar.findViewById(R.id.edittext_home_search);
        search.setOnEditorActionListener(new OnEditorActionListenerDone(this));
        fragmentManager = getSupportFragmentManager();

        if (twoPane)
        {
            int masterId = R.id.master_container;
            mHomefragment = (HomeFragment) fragmentManager.findFragmentById(masterId);
            if (mHomefragment == null)
            {
                mHomefragment = HomeFragment.getInstance(twoPane);
                mHomefragment.setmOnBookIsSelected(this);
                addFragmentToActivity(fragmentManager, mHomefragment, masterId);
            } else
            {
                showFragment(fragmentManager, mHomefragment);
            }

        } else
        {
            int contentID = R.id.contentFrame;
            mHomefragment = (HomeFragment) fragmentManager.findFragmentById(contentID);
            if (mHomefragment == null)
            {
                mHomefragment = HomeFragment.getInstance(twoPane);
                addFragmentToActivity(fragmentManager, mHomefragment, contentID);
            } else
            {
                showFragment(fragmentManager, mHomefragment);
            }

            Utility.scheduleChargingReminder(this);
        }
        new HomePresenter(mHomefragment, Inject.provideBooksRepository(this));

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
                SharedPrefHelper.getInstance(this).setSharedPreferenceRefreshAccesstoken("");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getBooks(String query)
    {
        search.clearFocus();
        mHomefragment.getBooks(query);
    }

    @Override
    public void bookSelected(BookItem bookItem)
    {
        int detailId = R.id.detail_container;
        BookDetailFragment bookDetailFragment = BookDetailFragment.getInstance(bookItem);
        fragmentManager.beginTransaction().replace(detailId, bookDetailFragment).commit();
        new BookDetailPresenter(bookDetailFragment, Inject.provideBooksRepository(this));
    }
}
