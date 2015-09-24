package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.GsonRequest;
import com.amazingcoders_android.api.Listener;
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

    public static GsonRequest<Venue> load(Long venueId, Listener<Venue> listener) {
        String url = String.format(Endpoint.VENUE, venueId);
        return new GsonRequest<>(Request.Method.GET, url, Venue.class, listener);
    }
}
