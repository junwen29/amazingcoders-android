package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Redemption;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 10/19/2015.
 */
public class RedemptionCard extends CardView {

    @InjectView(R.id.deal)
    TextView mDeal;
    @InjectView(R.id.venue)
    TextView mVenue;

    private Redemption mRedemption;

    public RedemptionCard(Context context) {
        super(context);
        init();
    }

    public RedemptionCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedemptionCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.redemption_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Redemption redemption){
        mRedemption = redemption;
        update();
    }

    private void update() {
        if (mRedemption == null) return;

        mDeal.setText(mRedemption.getDeal().getTitle());
        mVenue.setText(mRedemption.getVenue().getName());
    }
}