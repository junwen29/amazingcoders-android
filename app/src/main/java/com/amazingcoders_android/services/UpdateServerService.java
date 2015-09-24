package com.amazingcoders_android.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.api.requests.SettingsRequest;
import com.android.volley.VolleyError;

/**
 * Created by junwen29 on 9/23/2015.
 */
public class UpdateServerService extends Service {
    public static final String TAG = UpdateServerService.class.getSimpleName();

    public static final String UPDATE_DEVICE_TOKEN = "com.amazingcoders_android.UPDATE_DEVICE_TOKEN";

    public static Intent deviceToken(Context context, boolean isRegistering, String token) {
        return new Intent(context, UpdateServerService.class).setAction(UPDATE_DEVICE_TOKEN)
                .putExtra("is_registering", isRegistering)
                .putExtra("token", token);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Created");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroyed");
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Started");

        if (intent == null) return START_NOT_STICKY;

        String action = intent.getAction();

        switch (action){
            case UPDATE_DEVICE_TOKEN:
                boolean isRegistering = intent.getBooleanExtra("is_registering", false);
                String token = intent.getStringExtra("token");
                if (TextUtils.isEmpty(token)) return START_NOT_STICKY;
                if (isRegistering) {
                    registerDeviceToken(startId, token);
                } else {
                    unregisterDeviceToken(startId, token);
                }
                break;
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void registerDeviceToken(final int stopId, String token) {
        BurppleApi.getInstance(this).enqueue(SettingsRequest.registerDeviceToken(token, new EmptyListener() {
            @Override
            public void onResponse() {
                debugLog("Device token registered");
                stopSelf(stopId);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                debugLog("Failed to register device token");
                volleyError.printStackTrace();
                // TODO retry
                stopSelf(stopId);
            }
        }));
    }

    private void unregisterDeviceToken(final int stopId, String token) {
        BurppleApi.getInstance(this).enqueue(SettingsRequest.unregisterDeviceToken(token, new EmptyListener() {
            @Override
            public void onResponse() {
                debugLog("Device token unregistered");
                stopSelf(stopId);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                debugLog("Failed to unregister device token");
                volleyError.printStackTrace();
                stopSelf(stopId);
            }
        }));
    }

    private void debugLog(String message) {
        Log.d(TAG, message);
    }
}
