package com.amazingcoders_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.DealPageActivity;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.async_tasks.RegisterDealViewCountTask;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.views.DealCard;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/17/2015.
 */
public class DealAdapter extends ArrayAutoLoadAdapter<Deal> {
    private static final String TAG = "DealAdapter";

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

        return new ViewHolder(view, mContext);
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.card_deal)
        DealCard mDealCard;
        Context context;

        public ViewHolder(View v, Context ctx) {
            super(v);
            ButterKnife.inject(this,v);
            context = ctx;
            v.setOnClickListener(this);
        }

        public void update(Deal deal){
            mDealCard.update(deal);
        }

        @Override
        public void onClick(View v) {
            Long deal_id = mDealCard.getDealId();
            Intent i = new Intent(context, DealPageActivity.class);
            i.putExtra("deal_id", deal_id);
            context.startActivity(i);

            //register view count
            RegisterDealViewCountTask registerDealViewCountTask = new RegisterDealViewCountTask(context, TAG, deal_id);
            registerDealViewCountTask.execute(null, null, null);
        }

    }
}
