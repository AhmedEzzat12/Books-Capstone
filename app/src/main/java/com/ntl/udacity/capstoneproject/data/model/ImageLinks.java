package com.ntl.udacity.capstoneproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ImageLinks implements Parcelable
{
    public static final Creator<ImageLinks> CREATOR = new Creator<ImageLinks>()
    {
        @Override
        public ImageLinks createFromParcel(Parcel in)
        {
            return new ImageLinks(in);
        }

        @Override
        public ImageLinks[] newArray(int size)
        {
            return new ImageLinks[size];
        }
    };
    private String smallThumbnail;
    private String thumbnail;
    private String small;

    protected ImageLinks(Parcel in)
    {
        smallThumbnail = in.readString();
        thumbnail = in.readString();
        small = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(smallThumbnail);
        dest.writeString(thumbnail);
        dest.writeString(small);
    }

    public String getSmallThumbnail()
    {
        return smallThumbnail;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public String getSmall()
    {
        return small;
    }
}

