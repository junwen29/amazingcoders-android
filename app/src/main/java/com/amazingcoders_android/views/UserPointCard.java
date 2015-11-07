package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amazingcoders_android.Constants;
import com.amazingcoders_android.R;
import com.amazingcoders_android.helpers.AmazingHelper;
import com.amazingcoders_android.models.UserPoint;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 11/3/2015.
 */
public class UserPointCard extends CardView {
    @InjectView(R.id.reason)
    TextView mReason;
    @InjectView(R.id.created_at)
    TextView mCreatedAt;
    @InjectView(R.id.points_operation)
    TextView mPointsOperation;

    UserPoint mUserPoint;
    Context mContext;

    public UserPointCard(Context context) {
        super(context);
        init(context);
    }

    public UserPointCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UserPointCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.user_point_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(UserPoint userPoint){
        mUserPoint = userPoint;
        update();
    }

    private void update() {
        if (mUserPoint == null) return;

        mReason.setText(mUserPoint.getReason());
        String date = AmazingHelper.printDate(mUserPoint.getCreatedAt(), Constants.REDEMPTION_DATE_FORMAT);
        mCreatedAt.setText(date);
        mPointsOperation.setText(String.valueOf(mUserPoint.getPoints())+ " were "+ mUserPoint.getOperation()+"ed");
    }
}
