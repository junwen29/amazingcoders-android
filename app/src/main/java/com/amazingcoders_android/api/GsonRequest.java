package com.amazingcoders_android.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class GsonRequest<T> extends Request <T> {
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private final Map<String, String> mParams;
    private final Map<String, String> mHeaders;

    public GsonRequest(int method, String url, Class<T> clazz,
                       Listener<T> listener) {
        this(method, url, null, null, clazz, listener);
    }

    public GsonRequest(int method, String url, Map<String, String> params, Class<T> clazz,
                       Listener<T> listener) {
        this(method, url, params, null, clazz, listener);
    }

    public GsonRequest(int method, String url, Map<String, String> params, Map<String, String> headers,
                       Class<T> clazz, Listener<T> listener) {
        super(method, url, listener);
        mClazz = clazz;
        mListener = listener;
        mParams = params;
        mHeaders = headers;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return (mParams == null) ? super.getParams() : mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = (mHeaders == null) ? new HashMap<String, String>() : mHeaders;
        // Default headers
        headers.put("TIMEZONE", String.valueOf(BurppleApi.offsetFromUtc()));
        headers.put("Accept-Language", BurppleApi.acceptLanguage());
        headers.put("User-Agent", BurppleApi.userAgent());
        return headers;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            JsonElement je = new JsonParser().parse(json).getAsJsonObject().get("data");
            return Response.success(BurppleGson.getInstance().fromJson(je, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) mListener.onResponse(response);
    }
}
