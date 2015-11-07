package com.amazingcoders_android.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Yesha on 11/3/2015.
 */
public class UserPoint {

    @SerializedName("id")
    public final long id;
    @SerializedName("created_at")
    private Date createdAt;
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

    public int getPoints() {
        return this.points;
    }

    public Date getCreatedAt() {
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
