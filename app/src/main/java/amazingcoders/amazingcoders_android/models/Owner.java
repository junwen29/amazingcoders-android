package amazingcoders.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "owner")
public class Owner extends User {
    @DatabaseField
    @SerializedName("email")
    private String email;

    private transient String password;

    @DatabaseField
    @SerializedName("birthday")
    private String birthday;

    @DatabaseField
    @SerializedName("phone")
    private String phone;

    @SerializedName("auth_token")
    private String authToken;

    public Owner() {
        super();
    }

    public Owner(Owner source) {
        this();
        update(source);
    }

    public void update(Owner source) {
        firstName = source.firstName;
        lastName = source.lastName;
        username = source.username;
        email = source.email;
        phone = source.phone;
        birthday = source.birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Map<String, String> constructSignupParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("first_name", firstName);
        params.put("last_name", lastName);
        params.put("username", username);
        if (!TextUtils.isEmpty(password)) params.put("password", password);

        return params;
    }

    public Map<String, String> constructEditAccountParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("first_name", firstName);
        params.put("last_name", lastName);
        params.put("username", username);
        params.put("birthday", birthday);
        params.put("phone", phone);

        return params;
    }

    public Owner(Parcel in) {
        super(in);
        this.email = in.readString();
//		this.birthday = in.readString();
//		this.phone = in.readString();
    }

    public Owner(long id, String firstName) {
        super(id, firstName);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(email);
//		dest.writeString(birthday);
//		dest.writeString(phone);
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
