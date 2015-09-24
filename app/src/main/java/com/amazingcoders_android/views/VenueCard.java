package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Venue;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 9/21/2015.
 */
public class VenueCard extends CardView {
    @InjectView(R.id.name)
    TextView mName;
    //@InjectView(R.id.merchant_id)
    //TextView mMerchant_id;

    Venue mVenue;

    public VenueCard(Context context) {
        super(context);
        init();
    }

    public VenueCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VenueCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.venue_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Venue venue){
        mVenue = venue;
        update();
    }

    private void update() {
        if (mVenue == null) return;

        mName.setText(mVenue.getName());
        //mMerchant_id.setText(mVenue.getMerchant_ID().toString());
    }
}
