package com.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class Deal implements Parcelable, ActivityStream.ActivityStreamable {

    @SerializedName("id")
    public final long id;
    @SerializedName("title")
    private String title;
    @SerializedName("redeemable")
    private Boolean redeemable;
    @SerializedName("type_of_deal")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("location")
    private String location;
    @SerializedName("terms")
    private String terms;
    @SerializedName("num_of_redeems")
    private int num_of_redeems;

    public Deal(long id) {
        this.id = id;
    }

    protected Deal(Parcel in) {
        id = in.readLong();
        title = in.readString();
        type = in.readString();
        description = in.readString();
        location = in.readString();
        terms = in.readString();
        num_of_redeems = in.readInt();
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
//        dest.writeByte((byte) (redeemable ? 0x01 : 0x00));
        dest.writeString(type);
        dest.writeString(description);
//        dest.writeString(location);
//        dest.writeString(terms);
//        dest.writeInt(num_of_redeems);
    }

    public String getTitle() {
        return title;
    }

    public Boolean getRedeemable() {
        return redeemable;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getTerms() {
        return terms;
    }

    public int getNum_of_redeems() {
        return num_of_redeems;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String streamImageUrl() {
        return null;
    }

    @Override
    public Parcelable streamIntentExtra() {
        return null;
    }
}
