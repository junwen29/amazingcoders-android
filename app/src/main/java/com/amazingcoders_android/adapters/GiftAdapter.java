package com.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.models.Gift;
import com.amazingcoders_android.views.GiftCard;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Yesha on 11/2/2015.
 */
public class GiftAdapter extends ArrayAutoLoadAdapter<Gift> {

    public GiftAdapter(Context context, ArrayList<Gift> items) {
        super(context, items);
    }

    public GiftAdapter(Context context, int numHeaders) {
        super(context, numHeaders);
    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        Gift g = getItem(position);
        ((ViewHolder) holder).update(g);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_gift, viewGroup, false);

        return new ViewHolder(view);
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.card_gift)
        GiftCard mGiftCard;
        //Context context;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
            //context = ctx;
            mGiftCard.getRedeemButton().setOnClickListener(this);
        }

        public void update(Gift gift) {
            mGiftCard.update(gift);
        }

        @Override
        public void onClick(View v) {
                Long gift_id = mGiftCard.getGiftID();
                mGiftCard.redeemGift(gift_id);
        }
    }
}


