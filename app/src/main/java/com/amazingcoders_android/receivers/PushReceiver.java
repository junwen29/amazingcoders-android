package com.amazingcoders_android.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.DealPageActivity;
import com.amazingcoders_android.activities.MainActivity;
import com.amazingcoders_android.helpers.NotificationStore;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by junwen29 on 9/22/2015.
 */
public class PushReceiver extends WakefulBroadcastReceiver {

    public static final String FROM_NOTIFICATION_EXTRA = "from_notification";
    public static final String MESSAGE_EXTRA = "message";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationStore store = new NotificationStore(context);
        String[] unreadMessages;
        if (store.getUnreadMessages() != null) {
            Set<String> set = store.getUnreadMessages();
            unreadMessages = new String[set.size()];
            set.toArray(unreadMessages);
        }
        else {
            unreadMessages = new String[0];
        }

        int unreadCount = store.getUnreadCount() + 1;

        // Set inbox style
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        inboxStyle.setSummaryText(context.getString(R.string.push_summary));
        Set<String> stored = new LinkedHashSet<>();

        // Only store 4 previous messages TODO weird implementation here as it only ignores the first message if unread messages is more than 4
        for (int i = unreadMessages.length > 4 ? 1 : 0; i < unreadMessages.length; i++) {
//            inboxStyle.addLine(unreadMessages[i]); TODO remove as there is no other notification types yet
            stored.add(unreadMessages[i]);
        }
        String latest = intent.getStringExtra("message");
        inboxStyle.addLine(latest);
        stored.add(latest);

        // Set notification intent
        Intent notificationIntent = null;
        Bundle extras = intent.getExtras();
        String itemType = null;
        if(extras != null) {
            itemType = extras.getString("item_type");
        }

        String eventLabel = null;
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        if (itemType == null) {
            itemType = "";
        }
        //enter deal activity directly
        else if (itemType.equals("deal")) {
            Long dealId = Long.parseLong(extras.getString("item_id", "0"));
            if (dealId != 0) {
//                Deal deal = new Deal(dealId);
//                deal.setTitle(extras.getString("item_title"));
                notificationIntent = new Intent(context, DealPageActivity.class);
                // TODO track deal is pushed by food merchant
                notificationIntent
                        .putExtra("deal_id", dealId)
                        .putExtra("featured", true);
                eventLabel = latest;
            }
        }


        if (notificationIntent == null) {
            notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.putExtra("new_notification", true);
            eventLabel = "item_type: " + itemType;
//            stackBuilder.addParentStack(MainActivity.class);
        }
        notificationIntent.putExtra(FROM_NOTIFICATION_EXTRA, true)
                .putExtra(MESSAGE_EXTRA, eventLabel);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // configure the notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(extras != null ? extras.getString("item_name") : context.getResources().getString(R.string.app_name))
//                .setNumber(unreadCount)
                .setContentText(latest)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle);

        //long[] vibration = {0, 50, 100, 50};
//        if (latest != null && latest.contains("comment")) {
        builder.setDefaults(Notification.DEFAULT_ALL);
//        } else {
//            builder.setVibrate(null);
//        }

        // build the notification
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // get the notification manager and broadcast notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);

        // Persist current messages
        store.setUnreadCount(unreadCount);
        store.setUnreadMessages(stored);
    }
}
