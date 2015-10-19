package com.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.models.Redemption;
import com.amazingcoders_android.views.RedemptionCard;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 10/19/2015.
 */
public class RedemptionAdapter extends ArrayAutoLoadAdapter<Redemption> {

    public RedemptionAdapter(Context context, ArrayList<Redemption> items) {
        super(context, items);
    }

    public RedemptionAdapter(Context context, int numHeaders) {
        super(context, numHeaders);
    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        Redemption r = getItem(position);
        ((ViewHolder) holder).update(r);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_redemption,parent,false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_redemption)
        RedemptionCard mRedemptionCard;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }

        public void update(Redemption redemption){
            mRedemptionCard.update(redemption);
        }
    }
}
