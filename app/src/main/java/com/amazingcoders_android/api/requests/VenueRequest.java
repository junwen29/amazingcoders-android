package com.amazingcoders_android.api.requests;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonCollectionRequest;
import com.amazingcoders_android.models.Venue;

/**
 * Created by Yesha on 9/21/2015.
 */
public class VenueRequest {

    public static GsonCollectionRequest<Venue> allVenues(CollectionListener<Venue> listener){
        String url = Endpoint.VENUES;
        Type type = new TypeToken<Collection<Venue>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
