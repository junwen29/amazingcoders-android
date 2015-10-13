package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Deal;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 10/9/2015.
 */
public class DealDetailsCard extends CardView{

    @InjectView(R.id.desc)
    TextView mDesc;
    @InjectView(R.id.start_date)
    TextView mStartDate;
    @InjectView(R.id.end_date)
    TextView mEndDate;
    @InjectView(R.id.terms)
    TextView mTerms;

    @InjectView(R.id.desc_layout)
    RelativeLayout mDescContainer;

    Deal mDeal;

    public DealDetailsCard(Context context) {
        super(context);
        init();
    }

    public DealDetailsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DealDetailsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.deal_details_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Deal deal){
        mDeal = deal;
        update();
    }

    private void update() {
        if (mDeal == null) return;

        String start = "From: " + mDeal.getStart();
        String end = "Until: " + mDeal.getExpiry();
        mStartDate.setText(start);
        mEndDate.setText(end);
        mTerms.setText(mDeal.getTerms());
        mDesc.setText(mDeal.getDescription());
    }
}
