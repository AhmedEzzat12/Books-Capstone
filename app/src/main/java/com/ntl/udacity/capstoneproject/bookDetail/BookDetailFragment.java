package com.ntl.udacity.capstoneproject.bookDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntl.udacity.capstoneproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFragment extends Fragment
{


    public BookDetailFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

}
