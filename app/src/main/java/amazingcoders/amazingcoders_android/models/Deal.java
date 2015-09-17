package amazingcoders.amazingcoders_android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class Deal implements Parcelable{
    public final long id;
    private String title;
    private Boolean redeemable;
    private String type;
    private String description;
    private String location;
    private String terms;
    private int num_of_redeems;

    public Deal() {
        this.id = 0;
    }


    protected Deal(Parcel in) {
        id = in.readLong();
        title = in.readString();
        type = in.readString();
        description = in.readString();
        location = in.readString();
        terms = in.readString();
        num_of_redeems = in.readInt();
    }

    public static final Creator<Deal> CREATOR = new Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeByte((byte) (redeemable ? 0x01 : 0x00));
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(terms);
        dest.writeInt(num_of_redeems);
    }
}
