package com.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yesha on 9/21/2015.
 */
public class Venue implements Parcelable {

    @SerializedName("name")
    public final String NAME;
    //@SerializedName("merchant_id")
    //public Integer merchant_id;
    //@SerializedName("street")
    //public String street;
    //@SerializedName("address_2")
    //public String address;
    //@SerializedName("zipcode")
    //public String zipcode;
    //@SerializedName("city")
    //public String city;
    //@SerializedName("state")
    //public String state;
    //@SerializedName("country")
    //public String country;
    //@SerializedName("neighbourhood")
    //public String neighbourhood;
    //@SerializedName("phone")
    //public String phone;
    //@SerializedName("photo")
    //public String photo;

    public Venue() {
        this.NAME = "";
    }

    protected Venue(Parcel in) {
        this.NAME = in.readString();
        System.out.println("object name is" + this.NAME);
        //merchant_id = in.readInt();
        //street = in.readString();
        //address = in.readString();
        //zipcode = in.readString();
        //city = in.readString();
        //state = in.readString();
        //country = in.readString();
        //neighbourhood = in.readString();
        //phone = in.readString();
        //photo = in.readString();
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
        dest.writeString(NAME);
        //dest.writeLong(merchant_id);
     //   dest.writeString(street);
     //   dest.writeString(address);
     //   dest.writeString(zipcode);
     //   dest.writeString(city);
     //   dest.writeString(state);
     //   dest.writeString(country);
     //   dest.writeString(neighbourhood);
     //   dest.writeString(phone);
     //   dest.writeString(photo);
    }

    public String getName() {
        return this.NAME;
    }

    //public Integer getMerchant_ID() {
        //return this.merchant_id;
    //}
/*
    public String getStreet() {
        return this.street;
    }

    //public String getAddress() {
        return this.address;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public String getNeighbourhood() {
        return this.neighbourhood;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPhoto() {
        return this.photo;
    }
    */
}
