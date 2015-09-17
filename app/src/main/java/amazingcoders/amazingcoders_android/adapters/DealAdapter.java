package amazingcoders.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amazingcoders.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import amazingcoders.amazingcoders_android.models.Deal;
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

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_venue)
        ExploreVenueCard venueCard;
        @InjectView(R.id.button_wish)
        WishButton wishButton;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }

        private void updateWish(Venue v){
            wishButton.setVenue(v);
        }

        private void initHolder(Venue venue){
            venueCard.update(venue);
            updateWish(venue);

        }
    }
}
