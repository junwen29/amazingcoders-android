package com.amazingcoders_android.api;


import com.amazingcoders_android.BuildConfig;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class Endpoint {
    //    public static final String SERVER_URL = "http://10.0.0.2:3000/";
    public static final String SERVER_URL = BuildConfig.BUILD_TYPE.equals("debug") ? "http://192.168.0.100:3000/" : "https://amazingcodersrails.herokuapp.com/";
//    public static final String SERVER_URL = "http://127.0.0.1:3000/";
//    public static final String SERVER_URL = "https://amazingcodersrails.herokuapp.com/";
    //public static final String SERVER_URL = "http://192.168.0.101:3000/";
    public static final String P1_SERVER_URL = SERVER_URL + "api/p1/";

    // Device
    public static final String REGISTER_DEVICE = P1_SERVER_URL + "devices";
    public static final String UNREGISTER_DEVICE = P1_SERVER_URL + "devices?auth_token=%s&device_token=%s&device_type=android";

    public static final String SIGNUP = P1_SERVER_URL + "accounts/sign_up";
    public static final String LOGIN = P1_SERVER_URL + "accounts/sign_in";
    public static final String LOGOUT = P1_SERVER_URL + "accounts/sign_out";

    //TODO forget password page request
//    public static final String FORGOT_PASSWORD = WEB_URL + "reset_password";
    
    // TODO notifications
//    public static final String NOTIFICATION = P1_SERVER_URL + "notifications?auth_token=%s&offset=%s&limit=%s";
//    public static final String NOTIFICATION_COUNT = P1_SERVER_URL + "notifications/count?auth_token=%s";

    // Venues
    public static final String VENUES = P1_SERVER_URL + "venues?auth_token=%s";
    public static final String VENUE = P1_SERVER_URL + "venues/%s?auth_token=%s";

    // Deals
    public static final String DEALS = P1_SERVER_URL + "deals?auth_token=s";
    public static final String DEALS_TYPE = P1_SERVER_URL + "deals?type=%s&auth_token=%s";
    public static final String DEAL = P1_SERVER_URL + "deals/%s?auth_token=%s";
    public static final String DEALS_FOR_VENUE = P1_SERVER_URL + "venues/deals/%s?auth_token=%s";

    // Wish
    public static final String WISH = P1_SERVER_URL + "venues/%s/wishes";
    public static final String UNWISH = P1_SERVER_URL + "venues/%s/wishes?auth_token=%s";

    // Bookmark
    public static final String BOOKMARK = P1_SERVER_URL + "deals/%s/bookmarks";
    public static final String UNBOOKMARK = P1_SERVER_URL + "deals/%s/bookmarks?auth_token=%s";
}
