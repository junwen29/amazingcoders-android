package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

/**
 * Created by junwen29 on 10/20/2015.
 */
public class AnalyticsRequest {

    public static EmptyRequest registerRedemption(String dealId, EmptyListener listener) {
        String url = String.format(Endpoint.DEAL_REDEMPTION, BurppleApi.getAuthToken(), dealId);

        return new EmptyRequest(Request.Method.POST, url, listener);
    }

    public static EmptyRequest registerDealViewCount(String entry, long dealId, EmptyListener listener) {
        String url = String.format(Endpoint.DEAL_VIEWCOUNT, BurppleApi.getAuthToken(), dealId, entry);

        return new EmptyRequest(Request.Method.POST, url, listener);
    }

    public static EmptyRequest registerUserQuery(String query, String type, EmptyListener listener) {
        String url = String.format(Endpoint.DEAL_QUERY, BurppleApi.getAuthToken(), query, type);

        return new EmptyRequest(Request.Method.POST, url, listener);
    }
}
