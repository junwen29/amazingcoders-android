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
import com.amazingcoders_android.helpers.images.PicassoRequest;
import com.amazingcoders_android.models.Deal;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/17/2015.
 */
public class DealCard extends CardView {
    @InjectView(R.id.title)
    protected TextView mTitle;
    @InjectView(R.id.type)
    protected TextView mType;
    @InjectView(R.id.avatar)
    protected ImageView mAvatar;

    protected Deal mDeal;
    protected Context mContext;

    public DealCard(Context context) {
        super(context);
        init(context);
    }

    public DealCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DealCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mContext = context;
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
        Cloudinary cloudinary = BurppleApplication.getInstance(mContext).getCloudinary();
        int size = Helper.convertDipToPx(56, getResources());
        String url = cloudinary.url().transformation(new Transformation().height(size)).generate(mDeal.getImageUrl());
        PicassoRequest.get(mContext, url, R.drawable.ic_deal_white_placeholder).into(mAvatar);
    }

    public Long getDealId (){
        return this.mDeal.id;
    }
}
