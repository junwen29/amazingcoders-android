package com.amazingcoders_android.api.requests;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class DealRequest {

    public static GsonRequest<Deal> load(Long dealId, Listener<Deal> listener) {
        String url = String.format(Endpoint.DEAL, dealId);
        return new GsonRequest<>(Method.GET, url, Deal.class, listener);
    }

    public static GsonCollectionRequest<Deal> activeDeals(Map<String, String> params, CollectionListener<Deal> listener){
        String url = Endpoint.DEALS;
        Type type = new TypeToken<Collection<Deal>>(){}.getType();
        return new GsonCollectionRequest<>(Method.GET, url, params, type, listener);
    }

    /**
     * @param listener to return active freebies deals
     * @return Request for active freebie deals
     */
    public static GsonCollectionRequest<Deal> activeFreebiesDeals(CollectionListener<Deal> listener){
        Map<String, String> params = new HashMap<>();
        params.put("type", "freebies");
        return activeDeals(params, listener);
    }

    /**
     * @param listener to return active discount deals
     * @return Request for active discount deals
     */
    public static GsonCollectionRequest<Deal> activeDiscountDeals(CollectionListener<Deal> listener){
        Map<String, String> params = new HashMap<>();
        params.put("type", "discount");
        return activeDeals(params, listener);
    }
}

