package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

/**
 * Created by junwen29 on 10/9/2015.
 */
public class DealViewCountRequest {
    public static EmptyRequest register(String entry, long dealId, EmptyListener listener) {
        String url = String.format(Endpoint.DEAL_VIEWCOUNT, BurppleApi.getAuthToken(), dealId, entry);

        return new EmptyRequest(Request.Method.POST, url, listener);
    }
}
