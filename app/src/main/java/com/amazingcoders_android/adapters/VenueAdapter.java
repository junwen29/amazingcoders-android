package com.amazingcoders_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.VenueCard;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 9/21/2015.
 */
public class VenueAdapter extends ArrayAutoLoadAdapter<Venue> {

    public VenueAdapter(Context context, ArrayList<Venue> items) {
        super(context, items);
    }

    public VenueAdapter(Context context, int numHeaders) {
        super(context, numHeaders);
    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        Venue v = getItem(position);
        ((ViewHolder) holder).update(v);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_venue,viewGroup,false);

        return new ViewHolder(view, mContext);
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.card_venue)
        VenueCard mVenueCard;
        Context context;

        public ViewHolder(View v, Context ctx) {
            super(v);
            ButterKnife.inject(this, v);
            context = ctx;
            v.setOnClickListener(this);
        }

        public void update(Venue venue){
            mVenueCard.update(venue);
        }

        @Override
        public void onClick(View v) {
            //TextView id_TextView = (TextView) mVenueCard.findViewById(R.id.ID);
            //String venue_id_str = (String) id_TextView.getText();
            //Long venue_id = Long.valueOf(venue_id_str);
            Long venue_id = mVenueCard.getVenueID();
            Intent i = new Intent(this.context, VenuePageActivity.class);
            //Log.w("", "ID SENT IS = "+ venue_id);
            i.putExtra("id", venue_id);
            this.context.startActivity(i);
        }
    }

}
