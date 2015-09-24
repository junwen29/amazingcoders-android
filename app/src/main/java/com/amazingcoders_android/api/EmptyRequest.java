package com.amazingcoders_android.api;

import com.amazingcoders_android.api.requests.EmptyListener;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/23/2015.
 */
public class EmptyRequest extends Request<Void> {

    private final EmptyListener mListener;
    private final Map<String, String> mParams;

    public EmptyRequest(int method, String url,
                        EmptyListener listener) {
        this(method, url, null, listener);
    }

    public EmptyRequest(int method, String url, Map<String, String> params,
                        EmptyListener listener) {
        super(method, url, listener);
        mListener = listener;
        mParams = params;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return (mParams == null) ? super.getParams() : mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        // Default headers
        headers.put("TIMEZONE", String.valueOf(BurppleApi.offsetFromUtc()));
        headers.put("Accept-Language", BurppleApi.acceptLanguage());
        headers.put("User-Agent", BurppleApi.userAgent());
        return headers;
    }
    @Override
    protected void deliverResponse(Void response) {
        if (mListener != null) mListener.onResponse();
    }

    @Override
    protected Response<Void> parseNetworkResponse(NetworkResponse response) {
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
    }
}
