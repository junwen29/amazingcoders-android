package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonCollectionRequest;
import com.amazingcoders_android.models.Gift;
import com.amazingcoders_android.models.UserPoint;
import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by Yesha on 11/2/2015.
 */
public class GiftRequest {

    public static GsonCollectionRequest<Gift> allUserGifts(CollectionListener<Gift> listener){
        String url = String.format(Endpoint.GIFTS, BurppleApi.getAuthToken());
        Type type = new TypeToken<Collection<Gift>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static EmptyRequest redeem(Long id, EmptyListener listener) {
        String url = String.format(Endpoint.REDEEM_GIFT, BurppleApi.getAuthToken(),id);
        return new EmptyRequest(Request.Method.POST, url, listener);
    }

    public static GsonCollectionRequest<UserPoint> loadAll(String id, CollectionListener<UserPoint> listener) {
        String url = String.format(Endpoint.GIFT_REDEMPTIONS, id, BurppleApi.getAuthToken());
        Type type = new TypeToken<Collection<UserPoint>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
