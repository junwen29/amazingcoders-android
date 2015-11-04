package com.amazingcoders_android.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by junwen29 on 10/18/2015.
 */
public class Redemption {
    @SerializedName("id")
    public final long id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("user")
    private User user;
    @SerializedName("deal")
    private Deal deal;
    @SerializedName("venue")
    private Venue venue;
    @SerializedName("user_point")
    private UserPoint point;

    public Redemption() {
        this.id = 0;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Deal getDeal() {
        return deal;
    }

    public Venue getVenue() {
        return venue;
    }

    public UserPoint getPoint() {
        return point;
    }
}
