package com.amazingcoders_android;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 10/25/2015.
 */
public class BurppleApplication extends Application {
    private static final String TAG = "BurppleApplication";
    private Cloudinary mCloudinary;

    @Override
    public void onCreate() {
        super.onCreate();
        initCloudinary();
    }

    /**
     * @return An initialized Cloudinary instance
     */
    public Cloudinary getCloudinary() {
        if (mCloudinary == null)
            initCloudinary();

        return mCloudinary;
    }

    public static BurppleApplication getInstance(Context context) {
        return (BurppleApplication)context.getApplicationContext();
    }

    private void initCloudinary() {
        Map config = new HashMap();
        if (BuildConfig.BUILD_TYPE.equals("debug")){
            config.put("cloud_name", "dasf7qxuf");
            config.put("api_key", "458281774864292");
            config.put("api_secret", "_Kpia2LPcbNyxtoZLN3H31Z3Lj0");
        }
        else {
            config.put("cloud_name", "hiy2fjohv");
            config.put("api_key", "214212125991438");
            config.put("api_secret", "v594Nrqw7YlwF54dSknMzOlSud0");
        }

        mCloudinary = new Cloudinary(config);
        Log.d(TAG,"Cloudinary initialized");
    }

}
