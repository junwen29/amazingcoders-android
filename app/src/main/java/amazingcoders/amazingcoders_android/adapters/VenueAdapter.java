package amazingcoders.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amazingcoders.amazingcoders_android.R;
import amazingcoders.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import amazingcoders.amazingcoders_android.models.Venue;
import amazingcoders.amazingcoders_android.views.VenueCard;
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

        return new ViewHolder(view);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_venue)
        VenueCard mVenueCard;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }

        public void update(Venue venue){
            mVenueCard.update(venue);
        }
    }

}
