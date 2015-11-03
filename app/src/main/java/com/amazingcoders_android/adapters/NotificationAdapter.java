package com.amazingcoders_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.DealPageActivity;
import com.amazingcoders_android.activities.ProfilePageActivity;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.helpers.AmazingHelper;
import com.amazingcoders_android.models.Notification;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 11/3/2015.
 */
public class NotificationAdapter extends ArrayAutoLoadAdapter<Notification> {

    private static final String DEAL = "deal";
    private static final String BURPS = "burps";

    public NotificationAdapter(Context context, ArrayList<Notification> items) {
        super(context, items);
    }

    public NotificationAdapter(Context context, int numHeaders) {
        super(context, numHeaders);

    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Notification notification = getItem(position);

        // update the views
        ((ViewHolder)holder).mTitle.setText(notification.getItemName());
        Date time = notification.getCreatedAt();
        String timeAgo = AmazingHelper.getTimeAgo(mContext,time.getTime(),System.currentTimeMillis());
        ((ViewHolder) holder).mTime.setText(timeAgo);
        ((ViewHolder)holder).mMessage.setText(notification.getMessage());

        //set different on click functions
        switch (notification.getItemType()){
            case DEAL:
                // Deal page
                ((ViewHolder)holder).mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), DealPageActivity.class);
                        intent.putExtra("deal_id", notification.getItemId());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case BURPS:
                // Profile page
                ((ViewHolder)holder).mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProfilePageActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_notification, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView mTitle;
        @InjectView(R.id.time)
        TextView mTime;
        @InjectView(R.id.message)
        TextView mMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
