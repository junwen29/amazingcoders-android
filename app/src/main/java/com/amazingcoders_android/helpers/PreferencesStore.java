package com.amazingcoders_android.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;

import com.amazingcoders_android.BuildConfig;
import com.amazingcoders_android.models.Owner;

public class PreferencesStore {
    static final String TAG = "PreferencesHelper";
    // NOTE These key values should not be changed for backward-compatibility
    public static final String DEFAULT_PREFERENCE = "USER_PREF";
    public static final String USER_ID = "USERID"; //USER_ID is string
    public static final String EMAIL = "EMAIL";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String AUTH_TOKEN = "AUTH_TOKEN";
    public static final String COVER_UNLOCKED = "COVER_UNLOCKED";
    public static final String FACEBOOK_TOKEN = "FB_TOKEN";
    public static final String NEWBIE = "NEWBIE";
    public static final String GCM_REG_ID = "GCM_REG_ID"; // GCM Registration ID
    public static final String GCM_REG_APP_VERSION = "GCM_REG_APP_VERSION"; // App version for stored GCM ID
    public static final String SHARE_FACEBOOK = "SHARE_TO_FACEBOOK";

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

    public boolean isNewbie() {
        return mSharedPreferences.getBoolean(NEWBIE, true);
    }

    public void setNotNewbie() {
        mEditor.putBoolean(NEWBIE, false);
        mEditor.commit();
    }

    public void storeAccountInfo(Owner owner) {
        setBatchEdit(true);
        if (!TextUtils.isEmpty(owner.getAuthToken())) setAuthToken(owner.getAuthToken());
        setUserId(owner.id);
        setEmail(owner.getEmail());
        setFullName(owner.getFullName());
        commit();

        if (BuildConfig.DEBUG) logAllEntries();
    }

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

        /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    public void storeRegistrationId(Context context, String regId) {
//        int appVersion = Helper.getAppVersion(context);
        mEditor.putString(GCM_REG_ID, regId);
//        mEditor.putInt(GCM_REG_APP_VERSION, appVersion);
        mEditor.commit();
    }
}
