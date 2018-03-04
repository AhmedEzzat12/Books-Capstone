package com.ntl.udacity.capstoneproject.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BookShelfResponse implements Parcelable
{
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookShelfResponse> CREATOR = new Parcelable.Creator<BookShelfResponse>()
    {
        @Override
        public BookShelfResponse createFromParcel(Parcel in)
        {
            return new BookShelfResponse(in);
        }

        @Override
        public BookShelfResponse[] newArray(int size)
        {
            return new BookShelfResponse[size];
        }
    };
    private String Kind;
    private List<BookShelfItem> items;

    protected BookShelfResponse(Parcel in)
    {
        Kind = in.readString();
        if (in.readByte() == 0x01)
        {
            items = new ArrayList<>();
            in.readList(items, BookShelfItem.class.getClassLoader());
        } else
        {
            items = null;
        }
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(Kind);
        if (items == null)
        {
            dest.writeByte((byte) (0x00));
        } else
        {
            dest.writeByte((byte) (0x01));
            dest.writeList(items);
        }
    }

    public String getKind()
    {
        return Kind;
    }

    public List<BookShelfItem> getItems()
    {
        return items;
    }
}