package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonRequest;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.models.Redemption;
import com.android.volley.Request;

/**
 * Created by junwen29 on 10/17/2015.
 */
public class RedemptionRequest {

    public static GsonRequest<Redemption> load(String userId, String dealId, String venueId, Listener<Redemption> listener) {
        String url = String.format(Endpoint.REDEEM, BurppleApi.getAuthToken(), dealId, userId, venueId);
        return new GsonRequest<>(Request.Method.GET, url, Redemption.class, listener);
    }
}
