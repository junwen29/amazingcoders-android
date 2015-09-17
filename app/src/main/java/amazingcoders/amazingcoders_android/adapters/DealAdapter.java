package amazingcoders.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amazingcoders.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import amazingcoders.amazingcoders_android.models.Deal;
import amazingcoders.amazingcoders_android.views.DealCard;

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
        return new ViewHolder(new DealCard(mContext));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        DealCard mDealCard;

        public ViewHolder(View v) {
            super(v);
            mDealCard = (DealCard)v;
        }

        public void update(Deal deal){
            mDealCard.update(deal);
        }
    }
}
