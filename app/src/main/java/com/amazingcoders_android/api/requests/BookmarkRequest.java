package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/26/2015.
 */
public class BookmarkRequest {

    public static EmptyRequest bookmark(long dealId, EmptyListener listener) {
        String url = String.format(Endpoint.BOOKMARK, dealId);

        Map<String, String> params = new HashMap<String, String>();
        params.put("auth_token", BurppleApi.getAuthToken());

        return new EmptyRequest(Request.Method.POST, url, params, listener);
    }

    public static EmptyRequest unbookmark(long dealId, EmptyListener listener) {
        String url = String.format(Endpoint.UNBOOKMARK, dealId, BurppleApi.getAuthToken());

        return new EmptyRequest(Request.Method.DELETE, url, listener);
    }
}
