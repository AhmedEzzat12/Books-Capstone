package com.ntl.udacity.capstoneproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class SearchResponse implements Parcelable
{
    private String kind;
    private List<BookItem> items;

    protected SearchResponse(Parcel in)
    {
        kind = in.readString();
        items = in.createTypedArrayList(BookItem.CREATOR);
    }

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>()
    {
        @Override
        public SearchResponse createFromParcel(Parcel in)
        {
            return new SearchResponse(in);
        }

        @Override
        public SearchResponse[] newArray(int size)
        {
            return new SearchResponse[size];
        }
    };

    public List<BookItem> getItems()
    {
        if(items!=null)
        return items;
        return null;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(kind);
        dest.writeTypedList(items);
    }
}
