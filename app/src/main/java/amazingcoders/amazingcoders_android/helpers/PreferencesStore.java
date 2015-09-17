package amazingcoders.amazingcoders_android.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PreferencesStore {
    static final String TAG = "PreferencesHelper";
    // NOTE These key values should not be changed for backward-compatibility
    public static final String DEFAULT_PREFERENCE = "USER_PREF";
    public static final String USER_ID = "USERID"; //USER_ID is string
    public static final String EMAIL = "EMAIL";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String AUTH_TOKEN = "AUTH_TOKEN";
    public static final String COVER_UNLOCKED = "COVER_UNLOCKED";
    public static final String SIGNUP_COMPLETED = "SIGNUP_FLOW_FINISHED";
    public static final String INSTAGRAM_ENABLED = "SHARE_TO_INSTAGRAM";
    public static final String INSTAGRAM_TOKEN = "INSTAGRAM_ACCESS_TOKEN";
    public static final String FACEBOOK_TOKEN = "FB_TOKEN";
    public static final String INSTAGRAM_BOX_ID = "INSTAGRAM_BOX_ID";
    public static final String DEFAULT_BOX_ID = "DEFAULT_BOX_ID";
    public static final String NEWBIE = "NEWBIE";
    public static final String SELECTED_CITY_NAME = "SELECTED_CITY_NAME";
    public static final String SELECTED_CITY_ID = "SELECTED_CITY_ID";
    public static final String GCM_REG_ID = "GCM_REG_ID"; // GCM Registration ID
    public static final String GCM_REG_APP_VERSION = "GCM_REG_APP_VERSION"; // App version for stored GCM ID
    public static final String SHARE_FACEBOOK = "SHARE_TO_FACEBOOK";
    public static final String TWITTER_TOKEN = "TWITTER_TOKEN";
    public static final String TWITTER_SECRET = "TWITTER_SECRET";
    public static final String USER_HAS_LOCATION = "USER_HAS_LOCATION";
    public static final String RECENT_CATEGORIES_IDS = "RECENT_CATEGORIES_IDS";
    public static final String RECENT_NEIGHBOURHOODS_IDS = "RECENT_NEIGHBOURHOODS_IDS";

    private static final String RECENT_SG_NEIGHBOURHOODS_IDS = "RECENT_SG_NEIGHBOURHOODS_IDS";
    private static final String RECENT_SG_CATEGORIES_IDS = "RECENT_SG_CATEGORIES_IDS";

    private static final String RECENT_KL_NEIGHBOURHOODS_IDS = "RECENT_KL_NEIGHBOURHOODS_IDS";
    private static final String RECENT_KL_CATEGORIES_IDS = "RECENT_KL_CATEGORIES_IDS";

    private SharedPreferences mSharedPreferences;
    private Editor mEditor;
    private boolean mBatchEdit;

    public PreferencesStore(Context context) {
        mSharedPreferences = context.getSharedPreferences(DEFAULT_PREFERENCE, Activity.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void setBatchEdit(boolean on) {
        mBatchEdit = on;
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void setAuthToken(String token) {
        mEditor.putString(AUTH_TOKEN, token);
        if (!mBatchEdit) mEditor.commit();
    }

    public void clearAuthToken() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(AUTH_TOKEN);
        editor.commit();
    }

    public long getUserId() {
        return mSharedPreferences.getLong(USER_ID, 0);
    }

    public void setUserId(long id) {
        mEditor.putLong(USER_ID, id);
        if (!mBatchEdit) mEditor.commit();
    }

    public void setEmail(String email) {
        mEditor.putString(EMAIL, email);
        if(!mBatchEdit) mEditor.commit();
    }

    public String getEmail() {
        return mSharedPreferences.getString(EMAIL, null);
    }

    public void setFullName(String fullName) {
        mEditor.putString(FULL_NAME, fullName);
        if(!mBatchEdit) mEditor.commit();
    }

    public String getFullName() {
        return mSharedPreferences.getString(FULL_NAME, null);
    }

    public boolean isCoverUnlocked() {
        return mSharedPreferences.getBoolean(COVER_UNLOCKED, false);
    }

    public void setCoverUnlocked(boolean unlocked) {
        mEditor.putBoolean(COVER_UNLOCKED, unlocked);
        if (!mBatchEdit) mEditor.commit();
    }

    @Deprecated
    public boolean isSignupCompleted() {
        return mSharedPreferences.getBoolean(SIGNUP_COMPLETED, true);
    }

    @Deprecated
    public void setSignupCompleted(boolean completed) {
        mEditor.putBoolean(SIGNUP_COMPLETED, completed);
        if (!mBatchEdit) mEditor.commit();
    }

    public boolean isInstagramActive() {
        return isInstagramEnabled() && !TextUtils.isEmpty(getInstagramToken());
    }

    public boolean isInstagramEnabled() {
        return mSharedPreferences.getBoolean(INSTAGRAM_ENABLED, false);
    }

    public void setInstagramEnabled(boolean enabled) {
        mEditor.putBoolean(INSTAGRAM_ENABLED, enabled);
        if (!mBatchEdit) mEditor.commit();
    }

    public String getInstagramToken() {
        return mSharedPreferences.getString(INSTAGRAM_TOKEN, null);
    }

    public void setInstagramToken(String token) {
        mEditor.putString(INSTAGRAM_TOKEN, token);
        if (!mBatchEdit) mEditor.commit();
    }

    public boolean isFacebookShareEnabled() {
        return mSharedPreferences.getBoolean(SHARE_FACEBOOK, false);
    }

    public void setFacebookShareEnabled(boolean enabled) {
        mEditor.putBoolean(SHARE_FACEBOOK, enabled);
        if (!mBatchEdit) mEditor.commit();
    }

    public String getFacebookToken() {
        return mSharedPreferences.getString(FACEBOOK_TOKEN, null);
    }

    public void setFacebookToken(String token) {
        mEditor.putString(FACEBOOK_TOKEN, token);
        if (!mBatchEdit) mEditor.commit();
    }

    public long getInstagramBoxId() {
        return mSharedPreferences.getLong(INSTAGRAM_BOX_ID, 0);
    }

    public boolean isInstagramBox(long boxId) {
        return boxId != 0 && boxId == mSharedPreferences.getLong(INSTAGRAM_BOX_ID, 0);
    }

    public void setInstagramBoxId(long id) {
        mEditor.putLong(INSTAGRAM_BOX_ID, id);
        if (!mBatchEdit) mEditor.commit();
    }

    public long getDefaultBoxId() {
        return mSharedPreferences.getLong(DEFAULT_BOX_ID, 0);
    }

    public void setDefaultBoxId(long id) {
        mEditor.putLong(DEFAULT_BOX_ID, id);
        if (!mBatchEdit) mEditor.commit();
    }

    public boolean isNewbie() {
        return mSharedPreferences.getBoolean(NEWBIE, true);
    }

    public void setNotNewbie() {
        mEditor.putBoolean(NEWBIE, false);
        mEditor.commit();
    }

    public void setTwitterTokenAndSecret(String token, String secret) {
        mEditor.putString(TWITTER_TOKEN, token);
        mEditor.putString(TWITTER_SECRET, secret);
        mEditor.commit();
    }

    public String getTwitterToken() {
        return mSharedPreferences.getString(TWITTER_TOKEN, "");
    }

    public String getTwitterSecret() {
        return mSharedPreferences.getString(TWITTER_SECRET, "");
    }

    public void setUserHasLocation(boolean userHasLocation) {
        mEditor.putBoolean(USER_HAS_LOCATION, userHasLocation).commit();
    }

    public boolean userHasLocation() {
        return mSharedPreferences.getBoolean(USER_HAS_LOCATION, false);
    }

    public String getSelectedCityName() {
        return mSharedPreferences.getString(SELECTED_CITY_NAME, "");
    }

    public int getSelectedCityId() {
        return mSharedPreferences.getInt(SELECTED_CITY_ID, 0);
    }

    public void setSelectedCity(String cityName, int cityId) {
        mEditor.putString(SELECTED_CITY_NAME, cityName);
        mEditor.putInt(SELECTED_CITY_ID, cityId);
        mEditor.commit();
    }

//    public void storeAccountInfo(Owner owner) {
//        setBatchEdit(true);
//        if (!TextUtils.isEmpty(owner.getAuthToken())) setAuthToken(owner.getAuthToken());
//        setUserId(owner.id);
//        setEmail(owner.getEmail());
//        setFullName(owner.getFullName());
//        setCoverUnlocked(owner.isCoverUnlocked());
//        setSignupCompleted(owner.isSignupCompleted());
//        setDefaultBoxId(owner.getDefaultBoxId());
//        setInstagramBoxId(owner.getInstagramBoxId());
//        setInstagramEnabled(owner.isInstagramImportEnabled());
//        setInstagramToken(owner.getInstagramToken());
//        setUserHasLocation(owner.hasLocation());
//        commit();
//
//        if (BuildConfig.DEBUG) logAllEntries();
//    }

    public boolean commit() {
        return mEditor.commit();
    }

    public void clear() {
        mEditor.clear().commit();
    }

    public void logAllEntries() {
        Map<String, ?> entries = mSharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : entries.entrySet()) {
            Log.d(TAG, entry.getKey() + " : " + entry.getValue());
        }
    }

    public String getGCMRegistrationId() {
        return mSharedPreferences.getString(GCM_REG_ID, "");
    }

    public int getGCMAppVersion() {
        return mSharedPreferences.getInt(GCM_REG_APP_VERSION, Integer.MIN_VALUE);
    }

//    /**
//     * Stores the registration ID and app versionCode in the application's
//     * {@code SharedPreferences}.
//     *
//     * @param context application's context.
//     * @param regId registration ID
//     */
//    public void storeRegistrationId(Context context, String regId) {
//        int appVersion = Helper.getAppVersion(context);
//        mEditor.putString(GCM_REG_ID, regId);
//        mEditor.putInt(GCM_REG_APP_VERSION, appVersion);
//        mEditor.commit();
//    }

    public boolean storeRecentCategoriesID(List<Integer> ids){
        if(mSharedPreferences.getInt(RECENT_CATEGORIES_IDS + "_SIZE", 0) != 0 )
            mEditor.remove(RECENT_CATEGORIES_IDS + "_SIZE");

        mEditor.putInt(RECENT_CATEGORIES_IDS +"_SIZE", ids.size());

        for(int i=0;i<ids.size();i++) {
            if (mSharedPreferences.getInt(RECENT_CATEGORIES_IDS + "_" + i, 0) !=0)
                mEditor.remove(RECENT_CATEGORIES_IDS + "_" + i);
            mEditor.putInt(RECENT_CATEGORIES_IDS + "_" + i, ids.get(i));
        }

        return mEditor.commit();
    }

    public List<Integer> getRecentCategoriesId() {
        int size = mSharedPreferences.getInt(RECENT_CATEGORIES_IDS + "_SIZE", 0);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++)
            list.add(mSharedPreferences.getInt(RECENT_CATEGORIES_IDS + "_" + i, 0));
        return list;
    }

    public boolean storeRecentNeighbourhoodID(List<Integer> ids){
        if(mSharedPreferences.getInt(RECENT_NEIGHBOURHOODS_IDS + "_SIZE", 0) != 0 )
            mEditor.remove(RECENT_NEIGHBOURHOODS_IDS + "_SIZE");

        mEditor.putInt(RECENT_NEIGHBOURHOODS_IDS +"_SIZE", ids.size());

        for(int i=0;i<ids.size();i++) {
            if (mSharedPreferences.getInt(RECENT_NEIGHBOURHOODS_IDS + "_" + i, 0) !=0)
                mEditor.remove(RECENT_NEIGHBOURHOODS_IDS + "_" + i);
            mEditor.putInt(RECENT_NEIGHBOURHOODS_IDS + "_" + i, ids.get(i));
        }

        return mEditor.commit();
    }

    public List<Integer> getRecentNeighbourhoodId() {
        int size = mSharedPreferences.getInt(RECENT_NEIGHBOURHOODS_IDS + "_SIZE", 0);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++)
            list.add(mSharedPreferences.getInt(RECENT_NEIGHBOURHOODS_IDS + "_" + i, 0));
        return list;
    }

    public List<Integer> getRecentSgCategoriesId() {
        int size = mSharedPreferences.getInt(RECENT_SG_CATEGORIES_IDS + "_SIZE", 0);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++)
            list.add(mSharedPreferences.getInt(RECENT_SG_CATEGORIES_IDS + "_" + i, 0));
        return list;
    }

    public List<Integer> getRecentSgNeighbourhoodId() {
        int size = mSharedPreferences.getInt(RECENT_SG_NEIGHBOURHOODS_IDS + "_SIZE", 0);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++)
            list.add(mSharedPreferences.getInt(RECENT_SG_NEIGHBOURHOODS_IDS + "_" + i, 0));
        return list;
    }

    public List<Integer> getRecentKlCategoriesId() {
        int size = mSharedPreferences.getInt(RECENT_KL_CATEGORIES_IDS + "_SIZE", 0);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++)
            list.add(mSharedPreferences.getInt(RECENT_KL_CATEGORIES_IDS + "_" + i, 0));
        return list;
    }

    public List<Integer> getRecentKlNeighbourhoodId() {
        int size = mSharedPreferences.getInt(RECENT_KL_NEIGHBOURHOODS_IDS + "_SIZE", 0);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++)
            list.add(mSharedPreferences.getInt(RECENT_KL_NEIGHBOURHOODS_IDS + "_" + i, 0));
        return list;
    }

    public boolean storeRecentSgCategoriesId(List<Integer> ids) {
        if(mSharedPreferences.getInt(RECENT_SG_CATEGORIES_IDS + "_SIZE", 0) != 0 )
            mEditor.remove(RECENT_SG_CATEGORIES_IDS + "_SIZE");

        mEditor.putInt(RECENT_SG_CATEGORIES_IDS +"_SIZE", ids.size());

        for(int i=0;i<ids.size();i++) {
            if (mSharedPreferences.getInt(RECENT_SG_CATEGORIES_IDS + "_" + i, 0) !=0)
                mEditor.remove(RECENT_SG_CATEGORIES_IDS + "_" + i);
            mEditor.putInt(RECENT_SG_CATEGORIES_IDS + "_" + i, ids.get(i));
        }

        return mEditor.commit();
    }

    public boolean storeRecentSgNeighbourhoodId(List<Integer> ids) {
        if(mSharedPreferences.getInt(RECENT_SG_NEIGHBOURHOODS_IDS + "_SIZE", 0) != 0 )
            mEditor.remove(RECENT_SG_NEIGHBOURHOODS_IDS + "_SIZE");

        mEditor.putInt(RECENT_SG_NEIGHBOURHOODS_IDS +"_SIZE", ids.size());

        for(int i=0;i<ids.size();i++) {
            if (mSharedPreferences.getInt(RECENT_SG_NEIGHBOURHOODS_IDS + "_" + i, 0) !=0)
                mEditor.remove(RECENT_SG_NEIGHBOURHOODS_IDS + "_" + i);
            mEditor.putInt(RECENT_SG_NEIGHBOURHOODS_IDS + "_" + i, ids.get(i));
        }

        return mEditor.commit();
    }

    public boolean storeRecentKlCategoriesId(List<Integer> ids) {
        if(mSharedPreferences.getInt(RECENT_KL_CATEGORIES_IDS + "_SIZE", 0) != 0 )
            mEditor.remove(RECENT_KL_CATEGORIES_IDS + "_SIZE");

        mEditor.putInt(RECENT_KL_CATEGORIES_IDS +"_SIZE", ids.size());

        for(int i=0;i<ids.size();i++) {
            if (mSharedPreferences.getInt(RECENT_KL_CATEGORIES_IDS + "_" + i, 0) !=0)
                mEditor.remove(RECENT_KL_CATEGORIES_IDS + "_" + i);
            mEditor.putInt(RECENT_KL_CATEGORIES_IDS + "_" + i, ids.get(i));
        }

        return mEditor.commit();
    }

    public boolean storeRecentKlNeighbourhoodId(List<Integer> ids) {
        if(mSharedPreferences.getInt(RECENT_KL_NEIGHBOURHOODS_IDS + "_SIZE", 0) != 0 )
            mEditor.remove(RECENT_KL_NEIGHBOURHOODS_IDS + "_SIZE");

        mEditor.putInt(RECENT_KL_NEIGHBOURHOODS_IDS +"_SIZE", ids.size());

        for(int i=0;i<ids.size();i++) {
            if (mSharedPreferences.getInt(RECENT_KL_NEIGHBOURHOODS_IDS + "_" + i, 0) !=0)
                mEditor.remove(RECENT_KL_NEIGHBOURHOODS_IDS + "_" + i);
            mEditor.putInt(RECENT_KL_NEIGHBOURHOODS_IDS + "_" + i, ids.get(i));
        }

        return mEditor.commit();
    }
}
