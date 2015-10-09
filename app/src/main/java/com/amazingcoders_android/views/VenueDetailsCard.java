package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Venue;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 10/9/2015.
 */
public class VenueDetailsCard extends CardView{

    @InjectView(R.id.bio)
    TextView mBio;
    @InjectView(R.id.neighbourhood)
    TextView mNeighbourhood;
    @InjectView(R.id.street)
    TextView mStreet;
    @InjectView(R.id.zipcode)
    TextView mZipcode;
    @InjectView(R.id.phone)
    TextView mPhone;

    @InjectView(R.id.bio_layout)
    RelativeLayout mBioContainer;

    Venue mVenue;

    public VenueDetailsCard(Context context) {
        super(context);
        init();
    }

    public VenueDetailsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VenueDetailsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.venue_details_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Venue venue){
        mVenue = venue;
        update();
    }

    private void update() {
        if (mVenue == null) return;

        mNeighbourhood.setText(mVenue.getNeighbourhood());
        mPhone.setText(mVenue.getPhone());
        mZipcode.setText(mVenue.getZipcode());
        mStreet.setText(mVenue.getStreet());

        // check if Bio is blank
        String bio = mVenue.getBio();
        if (TextUtils.isEmpty(bio))
            mBioContainer.setVisibility(GONE);
        else{
            mBioContainer.setVisibility(VISIBLE);
            mBio.setText(bio);
        }
    }
}
