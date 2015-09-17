package amazingcoders.amazingcoders_android.api.requests;

import com.android.volley.Request.Method;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import amazingcoders.amazingcoders_android.api.CollectionListener;
import amazingcoders.amazingcoders_android.api.Endpoint;
import amazingcoders.amazingcoders_android.api.GsonCollectionRequest;
import amazingcoders.amazingcoders_android.models.Deal;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class DealRequest {

    public static GsonCollectionRequest<Deal> allDeals(CollectionListener<Deal> listener){
        String url = Endpoint.DEALS;
        Type type = new TypeToken<Collection<Deal>>(){}.getType();
        return new GsonCollectionRequest<>(Method.GET, url, type, listener);
    }
}

