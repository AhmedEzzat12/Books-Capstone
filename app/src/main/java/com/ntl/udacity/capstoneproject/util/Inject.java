package com.ntl.udacity.capstoneproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.ntl.udacity.capstoneproject.data.BooksRepository;
import com.ntl.udacity.capstoneproject.data.local.SharedPrefHelper;
import com.ntl.udacity.capstoneproject.data.remote.BooksRemoteDataSourcse;

import static com.google.common.base.Preconditions.checkNotNull;


public class Inject
{
    public static BooksRepository provideBooksRepository(@NonNull Context context)
    {
        checkNotNull(context);
        //ToDoDatabase database = ToDoDatabase.getInstance(context);
        return BooksRepository.getInstance(BooksRemoteDataSourcse.getInstance(context), SharedPrefHelper.getInstance(context));
    }
}
