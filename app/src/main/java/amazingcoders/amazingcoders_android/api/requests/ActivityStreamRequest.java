package amazingcoders.amazingcoders_android.api.requests;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import amazingcoders.amazingcoders_android.api.BurppleApi;
import amazingcoders.amazingcoders_android.api.CollectionListener;
import amazingcoders.amazingcoders_android.api.Endpoint;
import amazingcoders.amazingcoders_android.api.GsonCollectionRequest;
import amazingcoders.amazingcoders_android.api.GsonRequest;
import amazingcoders.amazingcoders_android.api.Listener;
import amazingcoders.amazingcoders_android.models.ActivityStream;

/**
 * Created by junwen29 on 9/23/2015.
 *
 * To load notifications and feed from rails server
 */
public class ActivityStreamRequest {

    public static GsonCollectionRequest<ActivityStream> notification(int offset, int limit, CollectionListener<ActivityStream> listener) {
        String url = String.format(Endpoint.NOTIFICATION, BurppleApi.getAuthToken(), offset, limit);
        Type type = new TypeToken<Collection<ActivityStream>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static GsonRequest<Integer> notificationCount(Listener<Integer> listener) {
        String url = String.format(Endpoint.NOTIFICATION_COUNT, BurppleApi.getAuthToken());
        return new GsonRequest<>(Request.Method.GET, url, Integer.class, listener);
    }
}
