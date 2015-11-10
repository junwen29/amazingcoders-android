package com.amazingcoders_android.api;

import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class VolleyErrorHelper {

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int CONFLICT = 409;

    public static int getHttpStatusCode(VolleyError e) {
        try {
            return e.networkResponse.statusCode;
        } catch (Exception exception) {
            return 0;
        }
    }

    public static String getResponse(VolleyError e) {
        String response = null;
        try {
            response = new String(e.networkResponse.data, "utf-8");
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        return response;
    }
}
