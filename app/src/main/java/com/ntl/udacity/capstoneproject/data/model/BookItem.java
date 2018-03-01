package com.ntl.udacity.capstoneproject.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class BookItem implements Parcelable
{
    public static final Creator<BookItem> CREATOR = new Creator<BookItem>()
    {
        @Override
        public BookItem createFromParcel(Parcel in)
        {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size)
        {
            return new BookItem[size];
        }
    };
    private String kind;
    private String id;
    private String selfLink;
    private VolumeInfo volumeInfo;

    protected BookItem(Parcel in)
    {
        kind = in.readString();
        id = in.readString();
        selfLink = in.readString();
        volumeInfo = in.readParcelable(VolumeInfo.class.getClassLoader());
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
        volumeInfo.writeToParcel(dest, 0);
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

    public VolumeInfo getVolumeInfo()
    {
        return volumeInfo;
    }

}
