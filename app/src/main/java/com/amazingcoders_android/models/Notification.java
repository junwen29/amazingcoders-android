package com.amazingcoders_android.models;

import com.amazingcoders_android.Constants;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by junwen29 on 11/3/2015.
 */
public class Notification {
    @SerializedName("id")
    public Long id;
    @SerializedName("item_type")
    private String itemType;
    @SerializedName("item_id")
    private Long itemId;
    @SerializedName("item_name")
    private String itemName;
    @SerializedName("message")
    private String message;
    @SerializedName("created_at")
    private Date createdAt;

    public String getItemType() {
        return itemType;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getMessage() {
        return message;
    }

    public String getItemName() {
        return itemName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Notification(Long id, String itemType, Long itemId, String itemName, String message, Date createdAt) {
        this.id = id;
        this.itemType = itemType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static class Deserializer implements JsonDeserializer<Notification> {
        @Override
        public Notification deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jo = (JsonObject) json;

            long id = jo.get("id").getAsLong();
            String itemType = jo.get("item_type").getAsString();
            Long itemId = jo.get("item_id").getAsLong();
            String itemName = jo.get("item_name").getAsString();
            String message = jo.get("message").getAsString();

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

            return new Notification(id, itemType, itemId, itemName, message, createdAt);
        }
    }
}
