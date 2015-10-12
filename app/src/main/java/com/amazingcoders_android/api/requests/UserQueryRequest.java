package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

/**
 * Created by junwen29 on 10/10/2015.
 */
public class UserQueryRequest {
    public static EmptyRequest register(String query, String type, EmptyListener listener) {
        String url = String.format(Endpoint.DEAL_QUERY, BurppleApi.getAuthToken(), query, type);

        return new EmptyRequest(Request.Method.POST, url, listener);
    }
}
