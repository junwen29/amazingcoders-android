package com.amazingcoders_android.api.requests;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonRequest;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.models.Owner;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class LoginRequest {

    private static GsonRequest<Owner> login(Map<String, String> params, Listener<Owner> listener) {
        String url = String.format(Endpoint.LOGIN);
        return new GsonRequest<>(Request.Method.POST, url, params, Owner.class, listener);
    }

    public static GsonRequest<Owner> email(String email, String password, Listener<Owner> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return login(params, listener);
    }

    public static GsonRequest<Owner> getOwner(String username, Listener<Owner> listener) {
        String url = String.format(Endpoint.PROFILE, BurppleApi.getAuthToken());
        return new GsonRequest<Owner>(Request.Method.GET, url, Owner.class, listener);
    }

//    public static GsonRequest<Owner> facebook(String token, Listener<Owner> listener) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("fb_token", token);
//
//        return login(params, listener);
//    }
//
//    public static GsonRequest<Owner> google(String token, Listener<Owner> listener) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("google_token", token);
//
//        return login(params, listener);
//    }
}
