package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.EmptyRequest;
import com.amazingcoders_android.api.Endpoint;
import com.android.volley.Request;

/**
 * Created by junwen29 on 10/29/2015.
 */
public class FeedbackRequest {

    public static EmptyRequest send(String title, String userId, String category, String desc, EmptyListener listener) {
        String url = String.format(Endpoint.SEND_FEEDBACK, BurppleApi.getAuthToken(), userId, title, category, desc);
        return new EmptyRequest(Request.Method.POST, url, listener);
    }

}
