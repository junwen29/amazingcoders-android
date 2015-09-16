package amazingcoders.amazingcoders_android.api;

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
//                    .setDateFormat(Constant.DATE_FORMAT)
//                    .registerTypeAdapter(ActivityStream.class, new ActivityStream.Deserializer())
//                    .registerTypeAdapter(Promotion.class, new Promotion.Deserializer())
//                    .registerTypeAdapter(Image.class, new Image.ImageUrlDeserializer())
//                    .registerTypeAdapter(Integer.class, new NotificationCountDeserializer())
                    .create();
        }
        return sInstance;
    }
}
