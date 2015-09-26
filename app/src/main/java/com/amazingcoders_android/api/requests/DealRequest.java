package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonCollectionRequest;
import com.amazingcoders_android.api.GsonRequest;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.models.Deal;
import com.android.volley.Request.Method;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class DealRequest {

    public static GsonRequest<Deal> load(Long dealId, Listener<Deal> listener) {
        String url = String.format(Endpoint.DEAL, dealId, BurppleApi.getAuthToken());
        return new GsonRequest<>(Method.GET, url, Deal.class, listener);
    }

    public static GsonCollectionRequest<Deal> activeDeals(CollectionListener<Deal> listener){
        Type type = new TypeToken<Collection<Deal>>(){}.getType();
        String url = String.format(Endpoint.DEALS, BurppleApi.getAuthToken());
        return new GsonCollectionRequest<>(Method.GET, url, type, listener);
    }

    public static GsonCollectionRequest<Deal> activeDeals(String url, CollectionListener<Deal> listener){
        Type type = new TypeToken<Collection<Deal>>(){}.getType();
        return new GsonCollectionRequest<>(Method.GET, url, type, listener);
    }

    /**
     * @param listener to return active freebies deals
     * @param type type of deal to filter
     * @return Request for active freebie deals
     */
    public static GsonCollectionRequest<Deal> activeDealsByType(String type, CollectionListener<Deal> listener){
        String url = String.format(Endpoint.DEALS_TYPE,type,BurppleApi.getAuthToken());
        return activeDeals(url, listener);
    }
}

