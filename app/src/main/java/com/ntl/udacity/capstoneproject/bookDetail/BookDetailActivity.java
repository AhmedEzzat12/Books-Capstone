package com.ntl.udacity.capstoneproject.bookDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.ntl.udacity.capstoneproject.R;

public class BookDetailActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar =findViewById(R.id.book_detail_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }
}
