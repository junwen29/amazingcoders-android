package com.amazingcoders_android.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.ProfilePageActivity;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.async_tasks.RedeemGiftTask;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Gift;
import com.android.volley.VolleyError;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 11/2/2015.
 */
public class GiftCard extends CardView {
    @InjectView(R.id.name)
    TextView mName;
    @InjectView(R.id.description)
    TextView mDescription;
    @InjectView(R.id.points)
    TextView mPoints;
    @InjectView(R.id.redeem_btn)
    Button redeemButton;

    Gift mGift;
    Context mContext;

    public GiftCard(Context context) {
        super(context);
        init(context);
    }

    public GiftCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.gift_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Gift gift){
        mGift = gift;
        update();
    }

    private void update() {
        if (mGift == null) return;

        mName.setText(mGift.getName());
        mDescription.setText(mGift.getDescription());
        mPoints.setText(String.valueOf(mGift.getPoints())+ " Burps");
        if (Global.with(this.getContext()).getOwnerBurps() >= mGift.getPoints()) {
            redeemButton.setEnabled(true);
            redeemButton.setBackgroundColor(0xFF009900);
            redeemButton.setTextColor(Color.WHITE);
        }
        else redeemButton.setEnabled(false);
    }

    public Long getGiftID() {
        return this.mGift.getId();
    }

    public void redeemGift(Long gift_id) {
        EmptyListener listener = new EmptyListener() {
            @Override
            public void onResponse() {
                Toast.makeText(getContext(), "You have successfully " +
                        "redeemed the gift. Please collect your gift at Burrple HQ", Toast.LENGTH_SHORT).show();
                getContext().startActivity(new Intent(getContext(), ProfilePageActivity.class));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Sorry, an error occurred " +
                        "while redeeming the gift. Please try again", Toast.LENGTH_SHORT).show();
            }
        };
        RedeemGiftTask mTask = new RedeemGiftTask(this.getContext(), gift_id, listener);
        mTask.execute((Void) null);
    }

    public Button getRedeemButton() {
        return this.redeemButton;
    }

}
