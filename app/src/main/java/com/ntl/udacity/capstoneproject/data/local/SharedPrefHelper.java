package com.ntl.udacity.capstoneproject.data.local;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper
{
    private final static String PREF_FILE_NAME = "PREF";
    private final static String PREF_TOKEN = "accesstoken";
    private final static String PREF_REFRESH = "refreshacctoken";
    private  static SharedPrefHelper INSTANCE = null;
    private final Context context;

    public SharedPrefHelper(Context context)
    {
        this.context = context;
    }

    public void setSharedPreferenceAccesstoken(String value)
    {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_TOKEN, value);
        editor.apply();
    }

    public void setSharedPreferenceRefreshAccesstoken(String value)
    {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_REFRESH, value);
        editor.apply();
    }

    public String getSharedPreferenceAccesstoken()
    {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
        return settings.getString(PREF_TOKEN, "");
    }

    public String getSharedPreferenceRefreshAccesstoken()
    {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
        return settings.getString(PREF_REFRESH, "");
    }

    public static SharedPrefHelper getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new SharedPrefHelper(context);
        }
        return INSTANCE;
    }
}
