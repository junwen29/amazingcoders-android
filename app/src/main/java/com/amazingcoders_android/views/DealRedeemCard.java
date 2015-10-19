package com.amazingcoders_android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Deal;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 10/14/2015.
 */
public class DealRedeemCard extends DealCard {
    @InjectView(R.id.redeem)
    RedeemButton mRedeemBtn;

    public DealRedeemCard(Context context) {
        super(context);
    }

    public DealRedeemCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DealRedeemCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.deal_redeem_card, this, true);
        ButterKnife.inject(this);
    }

    @Override
    public void update(Deal deal) {
        super.update(deal);
        mRedeemBtn.update(deal);
    }
}
