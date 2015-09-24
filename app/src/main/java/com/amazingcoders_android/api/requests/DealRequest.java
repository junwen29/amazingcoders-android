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

/**
 * Created by junwen29 on 9/15/2015.
 */
public class DealRequest {

    public static GsonCollectionRequest<Deal> allDeals(CollectionListener<Deal> listener){
        String url = Endpoint.DEALS;
        Type type = new TypeToken<Collection<Deal>>(){}.getType();
        return new GsonCollectionRequest<>(Method.GET, url, type, listener);
    }

    public static GsonRequest<Deal> load(Long dealId, Listener<Deal> listener) {
        String url = String.format(Endpoint.DEAL, dealId);
        return new GsonRequest<>(Method.GET, url, Deal.class, listener);
    }
}

