package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/23/2015.
 */
public class SettingsRequest {

    public static EmptyRequest registerDeviceToken(String token, EmptyListener listener) {
        String url = Endpoint.REGISTER_DEVICE;
        Map<String, String> params = new HashMap<String, String>();
        params.put("auth_token", BurppleApi.getAuthToken());
        params.put("device_token", token);
        params.put("device_type", "android_gcm");

        return new EmptyRequest(Request.Method.POST, url, params, listener);
    }

    public static EmptyRequest unregisterDeviceToken(String token, EmptyListener listener) {
        String url = String.format(Endpoint.UNREGISTER_DEVICE, BurppleApi.getAuthToken(), token);
        return new EmptyRequest(Request.Method.DELETE, url, listener);
    }
}
