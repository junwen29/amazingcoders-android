package amazingcoders.amazingcoders_android.helpers;

import android.content.Context;
import android.text.TextUtils;

import amazingcoders.amazingcoders_android.db.DatabaseHelper;
import amazingcoders.amazingcoders_android.models.Owner;

public class Global {
    private static Global sInstance;

    private Context mContext;
    private Owner mOwner;
    private long mOwnerId;

    public static Global with(Context context) {
        if (sInstance == null && context != null) {
            sInstance = new Global(context.getApplicationContext());
        }

        return sInstance;
    }

    public Global(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * Store Owner to static instance and underlying persistent storage.
     *
     * @param owner = app user
     */
    public void updateOwner(Owner owner) {
        if (owner == null)
            return;

        if (mOwner == null) {
            mOwner = owner;
        } else {
            mOwner.update(owner);
        }
        mOwnerId = owner.id;

        new PreferencesStore(mContext).storeAccountInfo(owner);

        DatabaseHelper.getInstance(mContext).getOwnerDao().createOrUpdateObject(owner);
    }

    public Owner getOwner() {
        if (mOwner == null) {
            Owner owner = DatabaseHelper.getInstance(mContext).getOwnerDao().getObject(getOwnerId());
            if (owner != null) {
                mOwner = owner;
            } else {
                mOwner = new Owner(getOwnerId(), "You");
            }
        }
        return mOwner;
    }

    public long getOwnerId() {
        if (mOwnerId == 0) {
            mOwnerId = new PreferencesStore(mContext).getUserId();
        }
        return mOwnerId;
    }

    public boolean isOwner(long id) {
        return getOwnerId() == id;
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(new PreferencesStore(mContext).getAuthToken());
    }

    public void reset() {
        mOwner = null;
        mOwnerId = 0;
    }
}
