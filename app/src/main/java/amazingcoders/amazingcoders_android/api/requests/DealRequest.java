package amazingcoders.amazingcoders_android.api.requests;

import com.android.volley.Request;

import amazingcoders.amazingcoders_android.api.Endpoint;
import amazingcoders.amazingcoders_android.api.GsonRequest;
import amazingcoders.amazingcoders_android.api.Listener;
import amazingcoders.amazingcoders_android.models.Deal;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class DealRequest {

    public static GsonRequest<Deal> allDeals(Listener<Deal> listener){
        String url = Endpoint.DEALS;
        return new GsonRequest<>(Request.Method.GET, url, Deal.class, listener);
    }
}
