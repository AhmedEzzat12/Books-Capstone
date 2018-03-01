package com.ntl.udacity.capstoneproject.home.plus;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


public class OnEditorActionListenerDone implements TextView.OnEditorActionListener
{
    private EditorActionOnClick actionOnClick;

    public OnEditorActionListenerDone(EditorActionOnClick actionOnClick)
    {
        this.actionOnClick = actionOnClick;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_DONE)
        {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
            {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                actionOnClick.getBooks(v.getText().toString());

            }
            return true;
        }
        return false;
    }
}
