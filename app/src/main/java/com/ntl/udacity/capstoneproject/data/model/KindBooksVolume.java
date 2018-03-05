package com.ntl.udacity.capstoneproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class KindBooksVolume implements Parcelable
{
    public static final Creator<KindBooksVolume> CREATOR = new Creator<KindBooksVolume>()
    {
        @Override
        public KindBooksVolume createFromParcel(Parcel in)
        {
            return new KindBooksVolume(in);
        }

        @Override
        public KindBooksVolume[] newArray(int size)
        {
            return new KindBooksVolume[size];
        }
    };
    private String kind;
    private List<BookItem> items;

    protected KindBooksVolume(Parcel in)
    {
        kind = in.readString();
        items = in.createTypedArrayList(BookItem.CREATOR);
    }

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
