package com.ntl.udacity.capstoneproject.bookshelfDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ntl.udacity.capstoneproject.R;

public class BookshelfDetailActivity extends AppCompatActivity
{

    public static final String BOOKSHELF_ID = "bookshlef_id";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);
        int bookshelfId = getIntent().getIntExtra(BOOKSHELF_ID, 0);


    }
}
