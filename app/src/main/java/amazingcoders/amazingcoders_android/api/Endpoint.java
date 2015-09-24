package amazingcoders.amazingcoders_android.api;


import amazingcoders.amazingcoders_android.BuildConfig;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class Endpoint {
    //    public static final String SERVER_URL = "http://10.0.0.2:3000/";
//    public static final String SERVER_URL = BuildConfig.BUILD_TYPE.equals("debug") ? "http://192.168.0.100:3000/" : "https://amazingcodersrails.herokuapp.com/";
//    public static final String SERVER_URL = "http://127.0.0.1:3000/";
//    public static final String SERVER_URL = "https://amazingcodersrails.herokuapp.com/";
    public static final String SERVER_URL = "http://192.168.0.102:3000/";
    public static final String P1_SERVER_URL = SERVER_URL + "api/p1/";

    public static final String SIGNUP = P1_SERVER_URL + "accounts/sign_up";
    public static final String LOGIN = P1_SERVER_URL + "accounts/sign_in";
    public static final String LOGOUT = P1_SERVER_URL + "accounts/sign_out";

//    public static final String FORGOT_PASSWORD = WEB_URL + "reset_password";

    public static final String DEALS = P1_SERVER_URL + "deals";
    public static final String VENUES = P1_SERVER_URL + "venues";
    public static final String SHOW_DEAL = P1_SERVER_URL + "deals/";
}
