package com.ntl.udacity.capstoneproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class VolumeInfo implements Parcelable
{
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VolumeInfo> CREATOR = new Parcelable.Creator<VolumeInfo>()
    {
        @Override
        public VolumeInfo createFromParcel(Parcel in)
        {
            return new VolumeInfo(in);
        }

        @Override
        public VolumeInfo[] newArray(int size)
        {
            return new VolumeInfo[size];
        }
    };
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private ImageLinks imageLinks;
    private String infoLink;
    private String description;
    private String averageRating;

    protected VolumeInfo(Parcel in)
    {
        title = in.readString();
        if (in.readByte() == 0x01)
        {
            authors = new ArrayList<>();
            in.readList(authors, String.class.getClassLoader());
        } else
        {
            authors = null;
        }
        publisher = in.readString();
        publishedDate = in.readString();
        imageLinks = (ImageLinks) in.readValue(ImageLinks.class.getClassLoader());
        infoLink = in.readString();
        description = in.readString();
        averageRating = in.readString();
    }

    public String getTitle()
    {
        return title;
    }

    public List<String> getAuthors()
    {
        return authors;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getPublishedDate()
    {
        return publishedDate;
    }

    public ImageLinks getImageLinks()
    {
        return imageLinks;
    }

    public String getInfoLink()
    {
        return infoLink;
    }

    public String getDescription()
    {
        return description;
    }

    public String getAverageRating()
    {
        return averageRating;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        if (authors == null)
        {
            dest.writeByte((byte) (0x00));
        } else
        {
            dest.writeByte((byte) (0x01));
            dest.writeList(authors);
        }
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeValue(imageLinks);
        dest.writeString(infoLink);
        dest.writeString(description);
        dest.writeString(averageRating);
    }
}