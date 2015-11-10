package com.amazingcoders_android.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yesha on 11/2/2015.
 */
public class Gift {
    @SerializedName("id")
    public final long id;
    @SerializedName("name")
    private String name;
    @SerializedName("points")
    private int points;
    @SerializedName("description")
    private String description;


    public Gift() {
        this.id = 0;
    }

    public Gift(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.points = in.readInt();
        this.description = in.readString();
    }

    public int getPoints() {
        return this.points;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
