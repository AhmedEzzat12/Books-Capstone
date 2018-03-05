package com.ntl.udacity.capstoneproject.bookshelfDetail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ntl.udacity.capstoneproject.R;
import com.ntl.udacity.capstoneproject.util.Inject;

import static com.ntl.udacity.capstoneproject.util.ActivityUtils.addFragmentToActivity;
import static com.ntl.udacity.capstoneproject.util.ActivityUtils.showFragment;

public class BookshelfDetailActivity extends AppCompatActivity
{

    public static final String BOOKSHELF_ID = "bookshlef_id";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);
        String bookshelfId = getIntent().getStringExtra(BOOKSHELF_ID);

        Toolbar toolbar = findViewById(R.id.bookshelfDetail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();

        BookshelfDetailFragment bookshelfDetailFragment = (BookshelfDetailFragment) fragmentManager.findFragmentById(R.id.contentFrame);
        if (bookshelfDetailFragment == null)
        {
            bookshelfDetailFragment = BookshelfDetailFragment.getInstance(bookshelfId);
            addFragmentToActivity(fragmentManager, bookshelfDetailFragment, R.id.contentFrame);

        } else
        {
            showFragment(fragmentManager, bookshelfDetailFragment);
        }
        new BookshelfDetailPresenter(bookshelfDetailFragment, Inject.provideBooksRepository(this));
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
        }
        return super.onOptionsItemSelected(item);
    }


}
