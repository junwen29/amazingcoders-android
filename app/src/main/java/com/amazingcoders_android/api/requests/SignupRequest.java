package com.amazingcoders_android.api.requests;

import com.android.volley.Request;

import java.util.Map;

import com.amazingcoders_android.api.Endpoint;
import com.amazingcoders_android.api.GsonRequest;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.models.Owner;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class SignupRequest {

    private static GsonRequest<Owner> signup(Map<String, String> params, Listener<Owner> listener) {
        String url = String.format(Endpoint.SIGNUP);
        return new GsonRequest<Owner>(Request.Method.POST, url, params, Owner.class, listener);
    }

    public static GsonRequest<Owner> email(Owner owner, Listener<Owner> listener) {
        Map<String, String> params = owner.constructSignupParams();
        return signup(params, listener);
    }

//    public static GsonRequest<Owner> facebook(Owner owner, String token, Listener<Owner> listener) {
//        Map<String, String> params = owner.constructSignupParams();
//        params.put("fb_token", token);
//
//        return signup(params, listener);
//    }
//
//    public static GsonRequest<Owner> google(Owner owner, String token, Listener<Owner> listener) {
//        Map<String, String> params = owner.constructSignupParams();
//        params.put("google_token", token);
//
//        return signup(params, listener);
//    }
}
