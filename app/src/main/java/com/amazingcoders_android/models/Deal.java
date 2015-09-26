package com.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.amazingcoders_android.sync.Synchronizable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class Deal implements Parcelable, Synchronizable {

    @SerializedName("id")
    public final long id;
    @SerializedName("title")
    private String title;
    @SerializedName("type_of_deal")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("t_c")
    private String terms;
    @SerializedName("num_of_redeems")
    private int num_of_redeems;
    @SerializedName("start_dsate")
    private String start;
    @SerializedName("expiry_date")
    private String expiry;
    @SerializedName("is_bookmarked")
    private boolean isBookmarked;

    public Deal(long id) {
        this.id = id;
    }

    public Deal() {
        this.id = 0;
    }

    protected Deal(Parcel in) {
        id = in.readLong();
        title = in.readString();
        type = in.readString();
        description = in.readString();
        terms = in.readString();
        num_of_redeems = in.readInt();
        start = in.readString();
        expiry = in.readString();
        isBookmarked = in.readByte() != 0x00;
    }

    public static final Creator<Deal> CREATOR = new Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(terms);
        dest.writeInt(num_of_redeems);
        dest.writeString(start);
        dest.writeString(expiry);
        dest.writeByte((byte) (isBookmarked? 0x01 : 0x00));
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getTerms() {
        return terms;
    }

    public int getNum_of_redeems() {
        return num_of_redeems;
    }

    public String getStart() {
        return start;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBookmarked(boolean bookmarked) {
        this.isBookmarked = bookmarked;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    @Override
    public long id() {
        return id;
    }
}

