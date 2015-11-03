package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonCollectionRequest;
import com.amazingcoders_android.models.Notification;
import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 11/3/2015.
 */
public class NotificationRequest {

    public static GsonCollectionRequest<Notification> loadAll(String userId, CollectionListener<Notification> listener){
        Type type = new TypeToken<Collection<Notification>>(){}.getType();
        String url = String.format(Endpoint.NOTIFICATIONS, BurppleApi.getAuthToken(), userId);
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
