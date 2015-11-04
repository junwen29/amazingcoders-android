package com.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.models.UserPoint;
import com.amazingcoders_android.views.UserPointCard;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 11/3/2015.
 */
public class UserPointAdapter extends ArrayAutoLoadAdapter<UserPoint> {

    public UserPointAdapter(Context context, ArrayList<UserPoint> items) {
        super(context, items);
    }

    public UserPointAdapter(Context context, int numHeaders) {
        super(context, numHeaders);
    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserPoint gr = getItem(position);
        ((ViewHolder) holder).update(gr);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_user_point, viewGroup, false);

        return new ViewHolder(view);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.card_user_point)
        UserPointCard mUserPointCard;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }

        public void update(UserPoint userPoint) {
            mUserPointCard.update(userPoint);
        }
    }
}
