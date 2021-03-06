package com.amazingcoders_android.api;


import com.amazingcoders_android.BuildConfig;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class Endpoint {
    //    public static final String SERVER_URL = "http://10.0.0.2:3000/";
    public static final String SERVER_URL = BuildConfig.BUILD_TYPE.equals("debug") ? "http://192.168.0.105:3000/" : "https://amazingcodersrails.herokuapp.com/";
//    public static final String SERVER_URL = "http://127.0.0.1:3000/";
//    public static final String SERVER_URL = "https://amazingcodersrails.herokuapp.com/";
    //public static final String SERVER_URL = "http://192.168.0.101:3000/";
    public static final String P1_SERVER_URL = SERVER_URL + "api/p1/";

    // Device
    public static final String REGISTER_DEVICE = P1_SERVER_URL + "devices";
    public static final String UNREGISTER_DEVICE = P1_SERVER_URL + "devices?auth_token=%s&device_token=%s&device_type=android";

    //User
    public static final String SIGNUP = P1_SERVER_URL + "accounts/sign_up";
    public static final String LOGIN = P1_SERVER_URL + "accounts/sign_in";
    public static final String LOGOUT = P1_SERVER_URL + "accounts/sign_out";
    public static final String PROFILE = P1_SERVER_URL + "accounts/profile?auth_token=%s";

    //TODO forget password page request
//    public static final String FORGOT_PASSWORD = WEB_URL + "reset_password";
    
    public static final String NOTIFICATIONS = P1_SERVER_URL + "notifications?auth_token=%s&user_id=%s";
//    public static final String NOTIFICATION_COUNT = P1_SERVER_URL + "notifications/count?auth_token=%s";

    // Venues
    public static final String VENUES = P1_SERVER_URL + "venues?auth_token=%s";
    public static final String VENUE = P1_SERVER_URL + "venues/%s?auth_token=%s";
    public static final String VENUES_FOR_DEAL = P1_SERVER_URL + "deals/venues/%s?auth_token=%s";

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

    // Analytics
    public static final String DEAL_VIEWCOUNT = P1_SERVER_URL + "analytics/deal/?auth_token=%s&deal_id=%s&entry=%s";
    public static final String DEAL_QUERY = P1_SERVER_URL + "analytics/query/?auth_token=%s&query=%s&type=%s";
    public static final String DEAL_REDEMPTION = P1_SERVER_URL + "analytics/redemption?auth_token=%s&deal_id=%s";

    // Deal Redemptions
    public static final String REDEEM = P1_SERVER_URL + "redemption?auth_token=%s&deal_id=%s&user_id=%s&venue_id=%s";
    public static final String ALL_REDEMPTIONS = P1_SERVER_URL + "redemption/index?auth_token=%s&user_id=%s";

    //Feedback
    public static final String SEND_FEEDBACK = P1_SERVER_URL + "feedback?auth_token=%s&user_id=%s&title=%s&category=%s&desc=%s";
    public static final String VIEW_FEEDBACKS = P1_SERVER_URL + "feedback/view?id=%s&auth_token=%s";

    //Gifts
    public static final String GIFTS = P1_SERVER_URL + "gifts?auth_token=%s";
    public static final String REDEEM_GIFT = P1_SERVER_URL + "gifts/redeem?auth_token=%s&id=%s";
    public static final String GIFT_REDEMPTIONS = P1_SERVER_URL + "gifts/records?id=%s&auth_token=%s";
}
