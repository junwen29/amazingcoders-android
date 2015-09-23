package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Deal;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/17/2015.
 */
public class DealCard extends CardView {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.type)
    TextView mType;

    Deal mDeal;

    public DealCard(Context context) {
        super(context);
        init();
    }

    public DealCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DealCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.deal_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Deal deal){
        mDeal = deal;
        update();
    }

    private void update() {
        if (mDeal == null) return;

        mTitle.setText(mDeal.getTitle());
        mType.setText(mDeal.getType());
    }
}
