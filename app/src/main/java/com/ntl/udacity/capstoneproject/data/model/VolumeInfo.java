package com.ntl.udacity.capstoneproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VolumeInfo implements Parcelable
{
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private ImageLinks imageLinks;
    private String infoLink;
    private String description;
    private String averageRating;

    public VolumeInfo(Parcel in)
    {
        title = in.readString();
        authors = in.createStringArrayList();
        publisher = in.readString();
        publishedDate = in.readString();
        averageRating = in.readString();
        description = in.readString();
        imageLinks = in.readParcelable(ImageLinks.class.getClassLoader());
        infoLink = in.readString();

    }

    public static final Creator<VolumeInfo> CREATOR = new Creator<VolumeInfo>()
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

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeStringList(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(averageRating);
        dest.writeString(description);
        imageLinks.writeToParcel(dest, 0);
        dest.writeString(infoLink);

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
}

