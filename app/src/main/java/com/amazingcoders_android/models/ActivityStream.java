package com.amazingcoders_android.models;

import android.os.Parcelable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.amazingcoders_android.Constants;

/**
 * Created by junwen29 on 9/23/2015.
 *
 *  * Generic model for feed or notification object.
 *
 * @param <T>
 */
public class ActivityStream <T extends ActivityStream.ActivityStreamable> {

    //TODO Existing Burpple activity stream which we will ignore but take note for future integration
    public static final int UNDEFINED = 0;
//    public static final int FOOD = 1;
//    public static final int COMMENT = 2;
//    public static final int MENTION = 3;
//    public static final int FOLLOW = 4;
//    public static final int LIKE = 5;
//    public static final int JOIN = 6;
//    public static final int BOX_LIKE = 7;

    // NOTE add new type here
    public static final int DEAL = 7;


    public final long id;
    public final int type;
    public final Date createdAt;

    private final T item;
    private String timeAgo;

    public ActivityStream(long id, int type, Date createdAt, T item) {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
        this.item = item;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public T getItem() {
        return item;
    }

    public static class Deserializer implements JsonDeserializer<ActivityStream> {
        @Override
        public ActivityStream deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jo = (JsonObject) json;

            long id = jo.get("id").getAsLong();
            String type = jo.get("item_type").getAsString();
            Date createdAt;
            try {
                // Might crash on some devices if not catching exception here
                createdAt = context.deserialize(jo.get("created_at"), Date.class);
            } catch (JsonSyntaxException je) {
                je.printStackTrace();
                DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                try {
                    createdAt = df.parse(jo.get("created_at").getAsString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    createdAt = new Date();
                }
            }

            //identify the item type of the activity
            switch (type.toLowerCase()){
                case "deal":
                    Deal deal = context.deserialize(jo.get("deal"),Deal.class);
                    return new ActivityStream<Deal>(id, DEAL, createdAt, deal);
                // Note: add new type here
                default:
                    return new ActivityStream<ActivityStreamable>(id, UNDEFINED, createdAt, null);
            }
        }
    }

    public static class Serializer implements JsonSerializer<ActivityStream> {
        @Override
        public JsonElement serialize(ActivityStream stream, Type typeOfT, JsonSerializationContext context) {
            JsonObject jo = new JsonObject();

            jo.addProperty("id", stream.id);
            jo.add("created_at", context.serialize(stream.createdAt, Date.class));

            switch (stream.type){
                case DEAL:
                    jo.addProperty("item_type", "deal");
                    jo.add("item", context.serialize(stream.getItem(),Deal.class));
                    break;

                // Note: add new type here

                default:
                    jo.addProperty("item_type", "undefined");
            }

            return jo;
        }
    }


    /**
     * Interface for object that can be shown in feed or notification.
     */
    public static interface ActivityStreamable {
        /**
         * Image url to be used e.g. in notification list item.
         *
         * @return Image url.
         */
        public String streamImageUrl();

        /**
         * Extras for intent when user click e.g. notification list item.
         *
         * @return Parcelable to be passed to intent extra.
         */
        public Parcelable streamIntentExtra();
    }
}
