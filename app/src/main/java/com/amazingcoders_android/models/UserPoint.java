package com.amazingcoders_android.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yesha on 11/3/2015.
 */
public class UserPoint {

    @SerializedName("id")
    public final long id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("user_id")
    private long user_id;
    @SerializedName("points")
    private int points;
    @SerializedName("reason")
    private String reason;
    @SerializedName("operation")
    private String operation;

    public UserPoint() {
        this.id = 0;
    }

    public UserPoint(Parcel in) {
        this.id = in.readLong();
        this.createdAt = in.readString();
        this.points = in.readInt();
        this.reason = in.readString();
        this.operation = in.readString();
    }

    public int getPoints() {
        return this.points;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public long getUser_id() {
        return this.user_id;
    }

    public String getReason() {
        return this.reason;
    }

    public String getOperation() {
        return this.operation;
    }
}
