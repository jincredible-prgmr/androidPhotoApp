package com.example.photos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

public class Photo implements java.io.Serializable, Parcelable {

    String path;
    String caption;
    ArrayList<Tag> tag;
    Calendar calendar;

    public Photo( String path) {
// com/example/photos/stock1.jpg
        this.path = path;
        this.tag = new ArrayList<Tag>();
        //this.caption = caption;
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND,0);

    }

    public String toString() {   // used by ListView
        return path;
    }


    protected Photo(Parcel in) {
        path = in.readString();
        caption = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(caption);
    }
}