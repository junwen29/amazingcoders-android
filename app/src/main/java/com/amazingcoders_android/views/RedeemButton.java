package com.amazingcoders_android.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.amazingcoders_android.models.Deal;

/**
 * Created by junwen29 on 10/14/2015.
 *
 *  checked = redeemed
 *  unchecked = redeemable
 */
public class RedeemButton extends Button {

    private static final String TAG = "RedeemButton";
    private Deal mDeal;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RedeemButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RedeemButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RedeemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RedeemButton(Context context) {
        super(context);
    }

    public void update(Deal deal) {
        boolean redeemable = deal != null && deal.isRedeemable();

        if (redeemable)
            setVisibility(VISIBLE);
        else
            setVisibility(GONE);

    }
}
