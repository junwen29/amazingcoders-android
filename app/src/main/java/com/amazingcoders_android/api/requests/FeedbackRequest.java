package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonCollectionRequest;
import com.amazingcoders_android.models.Feedback;
import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 10/29/2015.
 */
public class FeedbackRequest {

    public static EmptyRequest send(String title, String userId, String category, String desc, EmptyListener listener) {
        String url = String.format(Endpoint.SEND_FEEDBACK, BurppleApi.getAuthToken(), userId, title, category, desc);
        url = url.replaceAll(" ", "%20");
        return new EmptyRequest(Request.Method.POST, url, listener);
    }

    public static GsonCollectionRequest<Feedback> loadAll(String id, CollectionListener<Feedback> listener) {
        String url = String.format(Endpoint.VIEW_FEEDBACKS, id, BurppleApi.getAuthToken());
        Type type = new TypeToken<Collection<Feedback>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
