package amazingcoders.amazingcoders_android.api.requests;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import amazingcoders.amazingcoders_android.api.CollectionListener;
import amazingcoders.amazingcoders_android.api.Endpoint;
import amazingcoders.amazingcoders_android.api.GsonCollectionRequest;
import amazingcoders.amazingcoders_android.api.GsonRequest;
import amazingcoders.amazingcoders_android.api.Listener;
import amazingcoders.amazingcoders_android.models.Venue;

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
        String url = String.format(Endpoint.SHOW_VENUE);
        return new GsonRequest<Venue>(Request.Method.GET, url, Venue.class, listener);
    }
}
