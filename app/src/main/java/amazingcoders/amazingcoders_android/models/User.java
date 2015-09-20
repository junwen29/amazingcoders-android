package amazingcoders.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class User implements Parcelable{
    @DatabaseField(id = true)
    @SerializedName("id")
    public final long id;

    @DatabaseField
    @SerializedName("first_name")
    protected String firstName;

    @DatabaseField
    @SerializedName("last_name")
    protected String lastName;

    @DatabaseField
    @SerializedName("username")
    protected String username;

    private String fullName;

    public User() {
        this.id = 0;
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        if (fullName == null) {
            fullName = firstName;
            if (!TextUtils.isEmpty(lastName)) {
                fullName += " " + lastName;
            }
        }
        return fullName;
    }

    protected User(Parcel in) {
        id = in.readLong();
        firstName = in.readString();
        lastName = in.readString();
        username = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
