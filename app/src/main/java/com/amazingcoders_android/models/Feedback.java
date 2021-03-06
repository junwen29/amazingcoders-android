package com.amazingcoders_android.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yesha on 11/5/2015.
 */
public class Feedback {
    @SerializedName("title")
    public String title;
    @SerializedName("category")
    private String category;
    @SerializedName("content")
    private String content;
    @SerializedName("resolved")
    private boolean isResolved;


    public Feedback() {

    }

    public Feedback(Parcel in) {
        this.title = in.readString();
        this.category = in.readString();
        this.content = in.readString();
        this.isResolved = in.readByte() != 0x00;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCategory() {
        return this.category;
    }

    public String getContent() {
        return this.content;
    }

    public boolean isResolved() {
        return isResolved;
    }
}
