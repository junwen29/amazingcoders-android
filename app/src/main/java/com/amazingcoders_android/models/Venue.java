package com.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.amazingcoders_android.sync.Synchronizable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yesha on 9/21/2015.
 */
public class Venue implements Parcelable, Synchronizable {
    @SerializedName("id")
    public final Long id;
    @SerializedName("name")
    public String name;
    //@SerializedName("merchant_id")
    //public Integer merchant_id;
    @SerializedName("street")
    public String street;
    //@SerializedName("address_2")
    //public String address;
    @SerializedName("zipcode")
    public String zipcode;
    //@SerializedName("city")
    //public String city;
    //@SerializedName("state")
    //public String state;
    @SerializedName("bio")
    public String bio;
    @SerializedName("neighbourhood")
    public String neighbourhood;
    @SerializedName("phone")
    public String phone;
    @SerializedName("contact_number")
    public String contact_number;
    @SerializedName("is_wishlist")
    private boolean isWishlisted;
    @SerializedName("deals")
    private List<Deal> deals;


    public Venue() {
        this.id = (long) 0;
    }

    protected Venue(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        //System.out.println("object name is" + this.NAME);
        //merchant_id = in.readInt();
        street = in.readString();
        //address = in.readString();
        zipcode = in.readString();
        //city = in.readString();
        //state = in.readString();
        bio = in.readString();
        neighbourhood = in.readString();
        phone = in.readString();
        contact_number = in.readString();
        isWishlisted = in.readByte() != 0x00;
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        //dest.writeLong(merchant_id);
        dest.writeString(street);
        //   dest.writeString(address);
        dest.writeString(zipcode);
        //   dest.writeString(city);
        //   dest.writeString(state);
        dest.writeString(bio);
        dest.writeString(neighbourhood);
        dest.writeString(phone);
        dest.writeString(contact_number);
        dest.writeByte((byte) (isWishlisted ? 0x01 : 0x00));
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    /*
        public Integer getMerchant_ID() {
            return this.merchant_id;
        }
    */
    public String getStreet() {
        return this.street;
    }

    /*
        public String getAddress() {
            return this.address;
        }
    */
    public String getZipcode() {
        return this.zipcode;
    }

    /*
        public String getCity() {
            return this.city;
        }

        public String getState() {
            return this.state;
        }
    */
    public String getBio() {
        return this.bio;
    }

    public String getNeighbourhood() {
        return this.neighbourhood;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getContact_number() {
        return this.contact_number;
    }

    public boolean isWishlisted() {
        return isWishlisted;
    }

    public void setWishlisted(boolean wishlisted) {
        this.isWishlisted = wishlisted;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    @Override
    public long id() {
        return id;
    }
}
