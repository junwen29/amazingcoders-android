package com.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.views.DealCard;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/17/2015.
 */
public class DealAdapter extends ArrayAutoLoadAdapter<Deal> {

    public DealAdapter(Context context, ArrayList<Deal> items) {
        super(context, items);
    }

    public DealAdapter(Context context, int numHeaders) {
        super(context, numHeaders);
    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        Deal d = getItem(position);
        ((ViewHolder) holder).update(d);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_deal,viewGroup,false);

        return new ViewHolder(view);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_deal)
        DealCard mDealCard;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this,v);
        }

        public void update(Deal deal){
            mDealCard.update(deal);
        }
    }
}
