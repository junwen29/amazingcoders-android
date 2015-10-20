package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonCollectionRequest;
import com.amazingcoders_android.api.GsonRequest;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.models.Redemption;
import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 10/17/2015.
 */
public class RedemptionRequest {

    public static GsonRequest<Redemption> load(String userId, String dealId, String venueId, Listener<Redemption> listener) {
        String url = String.format(Endpoint.REDEEM, BurppleApi.getAuthToken(), dealId, userId, venueId);
        return new GsonRequest<>(Request.Method.POST, url, Redemption.class, listener);
    }

    public static GsonCollectionRequest<Redemption> loadAll(String userId, CollectionListener<Redemption> listener){
        Type type = new TypeToken<Collection<Redemption>>(){}.getType();
        String url = String.format(Endpoint.ALL_REDEMPTIONS, BurppleApi.getAuthToken(), userId);
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
