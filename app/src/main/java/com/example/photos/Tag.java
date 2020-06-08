package com.example.photos;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements java.io.Serializable, Parcelable {

    String type;
    String value;

    public Tag( String name, String value) {

        this.type = name;
        this.value = value;

    }

    protected Tag(Parcel in) {
        type = in.readString();
        value = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public boolean compareTag(Tag tag2) {

        if( tag2.type.equalsIgnoreCase(this.type.toLowerCase()) && this.value.toLowerCase().contains(tag2.value.toLowerCase()))
            return true;
        else return false;

    }

    public String toString() {

        return type + ": " + value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(value);
    }
}
