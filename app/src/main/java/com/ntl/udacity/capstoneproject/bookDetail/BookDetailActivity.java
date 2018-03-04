package com.ntl.udacity.capstoneproject.bookDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.data.model.BookItem;
import com.ntl.udacity.capstoneproject.myLibrary.MyLibraryActivity;
import com.ntl.udacity.capstoneproject.util.Inject;

import static com.ntl.udacity.capstoneproject.util.ActivityUtils.addFragmentToActivity;
import static com.ntl.udacity.capstoneproject.util.ActivityUtils.showFragment;

public class BookDetailActivity extends AppCompatActivity
{

    public static final String BOOK_BUNDLE = "book_bundle";
    public static final String BOOK = "book";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = findViewById(R.id.book_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getBundleExtra(BOOK_BUNDLE);
        BookItem bookItem = bundle.getParcelable(BOOK);
        FragmentManager fragmentManager = getSupportFragmentManager();

        BookDetailFragment bookDetailFragment = (BookDetailFragment) fragmentManager.findFragmentById(R.id.contentFrame);
        if (bookDetailFragment == null)
        {
            bookDetailFragment = BookDetailFragment.getInstance(bookItem);
            addFragmentToActivity(fragmentManager, bookDetailFragment, R.id.contentFrame);
        } else
        {
            showFragment(fragmentManager, bookDetailFragment);
        }

        new BookDetailPresenter(bookDetailFragment, Inject.provideBooksRepository(this));

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
            case android.R.id.home:
            {
                onBackPressed();
                return true;
            }
            case R.id.my_library:
                Intent intent = new Intent(BookDetailActivity.this, MyLibraryActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                //TODO handle logout
        }
        return super.onOptionsItemSelected(item);
    }
}
