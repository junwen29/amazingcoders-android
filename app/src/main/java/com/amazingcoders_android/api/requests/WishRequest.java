package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class WishRequest {

    public static EmptyRequest wish(long venueId, EmptyListener listener) {
        String url = String.format(Endpoint.WISH, venueId);

        Map<String, String> params = new HashMap<String, String>();
        params.put("auth_token", BurppleApi.getAuthToken());

        return new EmptyRequest(Request.Method.POST, url, params, listener);
    }

    public static EmptyRequest unwish(long venueId, EmptyListener listener) {
        String url = String.format(Endpoint.UNWISH, venueId, BurppleApi.getAuthToken());

        return new EmptyRequest(Request.Method.DELETE, url, listener);
    }
}
