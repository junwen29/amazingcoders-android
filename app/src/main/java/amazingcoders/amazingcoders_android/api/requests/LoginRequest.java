package amazingcoders.amazingcoders_android.api.requests;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import amazingcoders.amazingcoders_android.api.Endpoint;
import amazingcoders.amazingcoders_android.api.GsonRequest;
import amazingcoders.amazingcoders_android.api.Listener;
import amazingcoders.amazingcoders_android.models.Owner;

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
