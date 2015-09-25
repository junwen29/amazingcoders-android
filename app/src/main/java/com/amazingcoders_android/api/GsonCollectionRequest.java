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
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GsonCollectionRequest<T> extends Request<Collection<T>> {
    private final Type mType;
    private final CollectionListener<T> mListener;
    private final Map<String, String> mParams;
    private final Map<String, String> mHeaders;

    public GsonCollectionRequest(int method, String url, Type type,
                                 CollectionListener<T> listener) {
        this(method, url, null, null, type, listener);
    }

    public GsonCollectionRequest(int method, String url, Map<String,String> params, Type type,
                                 CollectionListener<T> listener){
        this(method,url,params, null, type, listener);
    }

    public GsonCollectionRequest(int method, String url, Map<String, String> params, Map<String, String> headers,
                                 Type type, CollectionListener<T> listener) {
        super(method, url, listener);
        mType = type;
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
    protected void deliverResponse(Collection<T> response) {
        if (mListener != null) mListener.onResponse(response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<Collection<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            JsonElement je = new JsonParser().parse(json).getAsJsonObject().get("data");
            return Response.success((Collection<T>) BurppleGson.getInstance().fromJson(je, mType),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
