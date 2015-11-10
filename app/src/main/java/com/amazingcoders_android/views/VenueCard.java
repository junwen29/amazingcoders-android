package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazingcoders_android.BurppleApplication;
import com.amazingcoders_android.R;
import com.amazingcoders_android.helpers.Helper;
import com.amazingcoders_android.helpers.images.PicassoCompat;
import com.amazingcoders_android.helpers.images.PicassoRequest;
import com.amazingcoders_android.models.Venue;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Yesha on 9/21/2015.
 */
public class VenueCard extends CardView {
    @InjectView(R.id.name)
    TextView mName;
    @InjectView(R.id.neighbourhood)
    TextView mNeighbourhood;
    @InjectView(R.id.avatar)
    ImageView mAvatar;

    Venue mVenue;
    Context mContext;

    public VenueCard(Context context) {
        super(context);
        init(context);
    }

    public VenueCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VenueCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
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
        mNeighbourhood.setText(mVenue.getNeighbourhood());
        Cloudinary cloudinary = BurppleApplication.getInstance(mContext).getCloudinary();
        int size = Helper.convertDipToPx(56,getResources());
        String url = cloudinary.url().transformation(new Transformation().height(size)).generate(mVenue.getPhotoUrl());
        PicassoRequest.get(mContext,url, R.drawable.ic_place_white_placeholder).fit().into(mAvatar);
    }

    public Long getVenueID() {
        return this.mVenue.getId();
    }

    public TextView getmName() {
        return mName;
    }
}
