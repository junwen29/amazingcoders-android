package com.amazingcoders_android.api;

import com.amazingcoders_android.Constants;
import com.amazingcoders_android.models.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by junwen29 on 9/15/2015.
 */
public class BurppleGson {

    private static Gson sInstance;

    public static Gson getInstance() {
        if (sInstance == null) {
            sInstance = new GsonBuilder()
                    .setDateFormat(Constants.DATE_FORMAT)
                    .registerTypeAdapter(Notification.class, new Notification.Deserializer())
//                    .registerTypeAdapter(Promotion.class, new Promotion.Deserializer())
//                    .registerTypeAdapter(Image.class, new Image.ImageUrlDeserializer())
//                    .registerTypeAdapter(Integer.class, new NotificationCountDeserializer())
                    .create();
        }
        return sInstance;
    }
}
