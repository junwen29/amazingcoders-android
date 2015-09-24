package com.amazingcoders_android.api.requests;

import com.android.volley.Response;

/**
 * Created by junwen29 on 9/23/2015.
 */
public interface EmptyListener extends Response.ErrorListener {
    public void onResponse();
}