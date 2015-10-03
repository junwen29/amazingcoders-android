package com.amazingcoders_android.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.amazingcoders_android.helpers.OkHttpStack;
import com.amazingcoders_android.helpers.PreferencesStore;

@SuppressLint("DefaultLocale")
public class BurppleApi {
    static final String TAG = "BurppleApi";
    static final String ENDPOINT_CALL = "Endpoint";
    static final int DEFAULT_TIMEOUT = 20000; // 10 s
    static final int DEFAULT_MAX_RETRY = 0;

    private static BurppleApi sInstance;

    private RequestQueue mRequestQueue;

    private String mUserAgent;
    private String mAuthToken;
    private DefaultRetryPolicy mDefaultRetryPolicy = new DefaultRetryPolicy(DEFAULT_TIMEOUT, DEFAULT_MAX_RETRY,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public static BurppleApi getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BurppleApi(context.getApplicationContext());
        }
        sInstance.initAuthToken(context);
        sInstance.initUserAgent(context);

        return sInstance;
    }

    public static String getAuthToken() {
        return sInstance == null ? "" : sInstance.getToken();
    }

    public String getToken() {
        return mAuthToken;
    }

    public static String userAgent() {
        return sInstance == null ? "" : sInstance.getUserAgent();
    }

    public String getUserAgent() {
        return mUserAgent;
    }

    public void resetToken() {
        mAuthToken = null;
    }

    public static String acceptLanguage() {
        return Locale.getDefault().getLanguage() +
                "-" +
                Locale.getDefault().getCountry().toLowerCase(Locale.US);
    }

    public static int offsetFromUtc() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 1000;
    }

    public BurppleApi(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack(context));
    }

    /**
     * Cancel all requests with the corresponding tag.
     * This should be called in onStop() method of the activity.
     *
     * @param tag Tag of requests to be cancelled.
     */
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
        Log.d(TAG, "Cancelling requests with tag: " + tag);
    }

    /**
     * Get auth token from preferences if not already set.
     *
     * @param context
     */
    public void initAuthToken(Context context) {
        mAuthToken = new PreferencesStore(context.getApplicationContext()).getAuthToken();
    }

    public void initUserAgent(Context context) {
        if (TextUtils.isEmpty(mUserAgent)) {
            try {
                mUserAgent = "Burpple Android/" + context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void enqueue(Request<?> req) {
        req.setRetryPolicy(mDefaultRetryPolicy);
        mRequestQueue.add(req);

        Log.d(ENDPOINT_CALL, req.getUrl());
    }

    public void enqueue(Request<?> req, Object tag) {
        if (tag != null) {
            req.setTag(tag);

            Log.d(TAG, "Send request with tag: " + tag);
        }

        enqueue(req);
    }

    public static String sanitizeParams(String params) {
        try {
            params = URLEncoder.encode(params, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            params = params.replace(' ', '+');
        }

        return params;
    }
}
