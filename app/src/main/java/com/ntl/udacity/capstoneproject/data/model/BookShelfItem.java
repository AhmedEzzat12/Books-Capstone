package com.ntl.udacity.capstoneproject.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class BookShelfItem implements Parcelable
{
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookShelfItem> CREATOR = new Parcelable.Creator<BookShelfItem>()
    {
        @Override
        public BookShelfItem createFromParcel(Parcel in)
        {
            return new BookShelfItem(in);
        }

        @Override
        public BookShelfItem[] newArray(int size)
        {
            return new BookShelfItem[size];
        }
    };
    private String kind;
    private String id;
    private String selfLink;
    private String title;
    private String volumeCount;

    protected BookShelfItem(Parcel in)
    {
        kind = in.readString();
        id = in.readString();
        selfLink = in.readString();
        title = in.readString();
        volumeCount = in.readString();
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
        dest.writeString(id);
        dest.writeString(selfLink);
        dest.writeString(title);
        dest.writeString(volumeCount);
    }

    public String getKind()
    {
        return kind;
    }

    public String getId()
    {
        return id;
    }

    public String getSelfLink()
    {
        return selfLink;
    }

    public String getTitle()
    {
        return title;
    }

    public String getVolumeCount()
    {
        return volumeCount;
    }
}