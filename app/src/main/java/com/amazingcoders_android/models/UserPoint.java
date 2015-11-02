package com.amazingcoders_android.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by junwen29 on 11/2/2015.
 */
public class UserPoint {

    @SerializedName("id")
    public final long id;

    @SerializedName("reason")
    private String reason;
    @SerializedName("points")
    private int points;
    @SerializedName("operation")
    private String operation;
    @SerializedName("user")
    private User user;

    public UserPoint(long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }
}
