package com.ntl.udacity.capstoneproject.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.common.base.Preconditions;


public class ActivityUtils
{
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int contentFrame)
    {

        Preconditions.checkNotNull(fragment);
        Preconditions.checkNotNull(fragmentManager);
        fragmentManager
                .beginTransaction()
                .add(contentFrame, fragment)
                .commit();
    }


    public static void showFragment(FragmentManager fragmentManager, Fragment fragment)
    {

        Preconditions.checkNotNull(fragment);
        Preconditions.checkNotNull(fragmentManager);
        fragmentManager
                .beginTransaction()
                .show(fragment)
                .commit();
    }


}
