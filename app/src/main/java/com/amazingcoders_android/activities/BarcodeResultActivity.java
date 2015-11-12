package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazingcoders_android.Constants;
import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.VolleyErrorHelper;
import com.amazingcoders_android.api.requests.RedemptionRequest;
import com.amazingcoders_android.async_tasks.RegisterRedemptionTask;
import com.amazingcoders_android.helpers.AmazingHelper;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Redemption;
import com.amazingcoders_android.models.UserPoint;
import com.amazingcoders_android.views.DealCard;
import com.amazingcoders_android.views.DealDetailsCard;
import com.amazingcoders_android.views.VenueDetailsCard;
import com.android.volley.VolleyError;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BarcodeResultActivity extends BaseActivity {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeResultActivity";
    private static final int COUNT_DOWN_INTERVAL = 120000;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.barcode_container)
    LinearLayout mContainer;
    @InjectView(R.id.card_deal)
    DealCard mDealCard;
    @InjectView(R.id.card_deal_details)
    DealDetailsCard mDealDetailsCard;
    @InjectView(R.id.card_venue)
    VenueDetailsCard mVenueCard;
    @InjectView(R.id.redeem_time)
    TextView mRedeemTime;
    @InjectView(R.id.redeem_title)
    TextView mRedeemTitle;
    @InjectView(R.id.redeem_message)
    TextView mRedeemMessage;
    @InjectView(R.id.container)
    LinearLayout mCardContainer;
    @InjectView(R.id.redeem_progress)
    View mProgressBar;
    @InjectView(R.id.card_redeem)
    CardView mRedeeemCard;
    @InjectView(R.id.redeem_animation)
    View mProgressAnimation;
    @InjectView(R.id.points)
    TextView mPoints;
    @InjectView(R.id.burps_layout)
    RelativeLayout mPointsLayout;
    @InjectView(R.id.timer)
    TextView mCountdownTimer;
    @InjectView(R.id.timer_layout)
    View mTimerLayout;

    private String mUserId, mDealId, mVenueId;

    private Barcode mBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableScreenShot(); //disable screenshot
        setContentView(R.layout.activity_barcode_result);

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mBarcode = getIntent().getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

        if (mBarcode == null) {
            Snackbar.make(mContainer, R.string.barcode_error,
                    Snackbar.LENGTH_LONG)
                    .show();
        } else {
            Snackbar.make(mContainer, R.string.barcode_success,
                    Snackbar.LENGTH_LONG)
                    .show();
            constructParams(mBarcode.displayValue);
            redeem();
        }
    }

    @Override
    public void onBackPressed() {
        //override to resolve memory issue: release the activity instead
        startActivity(new Intent(this, DealsFeedActivity.class));
        finishAffinity();
    }

    private void redeem(){
        //Check QR code
        if (TextUtils.isEmpty(mDealId) ||TextUtils.isEmpty(mVenueId)){
            showResult(false, "Invalid QR code");
            return;
        } else if (!mDealId.matches("\\d+") || !mVenueId.matches("\\d+")){
            showResult(false, "Invalid QR code");
            return;
        }

        mUserId = Long.toString(Global.with(this).getOwnerId());

        Listener<Redemption> listener = new Listener<Redemption>() {
            @Override
            public void onResponse(Redemption redemption) {
                //TODO convert date time to user friendly text
                String redeemTime = "Time: "+ AmazingHelper.printDate(redemption.getDate(), Constants.REDEMPTION_DATE_FORMAT);
                showResult(true, "");
                mDealCard.update(redemption.getDeal());
                mRedeemTime.setText(redeemTime);
                mDealDetailsCard.update(redemption.getDeal());
                mVenueCard.update(redemption.getVenue());
                mPointsLayout.setVisibility(View.VISIBLE);
                UserPoint userPoint = redemption.getPoint();
                if (userPoint != null){
                    String point = Integer.toString(userPoint.getPoints());
                    mPoints.setText(point);
                    mPoints.setTextColor(getResources().getColor(R.color.green));
                } else {
                    String message = "Only for 1st redeem";
                    mPoints.setText(message);
                    mPoints.setTextColor(getResources().getColor(R.color.color_primary));
                }
//                 set count down timer and destroy activity upon two minutes
                RedemptionCountDownTimer countDownTimer = new RedemptionCountDownTimer(COUNT_DOWN_INTERVAL, 1000, mCountdownTimer);
                countDownTimer.start();

                RegisterRedemptionTask registerRedemptionTask = new RegisterRedemptionTask(BarcodeResultActivity.this, mDealId);
                registerRedemptionTask.execute(null, null, null);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d(TAG, volleyError.networkResponse.);
                String response = VolleyErrorHelper.getResponse(volleyError);
                int statusCode = VolleyErrorHelper.getHttpStatusCode(volleyError);
                mPointsLayout.setVisibility(View.GONE);
                String message = "";
                switch (statusCode){
                    case VolleyErrorHelper.NOT_ACCEPTABLE:
                        message = new JsonParser().parse(response)
                                .getAsJsonObject()
                                .get("error")
                                .getAsJsonObject()
                                .get("message")
                                .getAsString();

                        showResult(false, message);
                        break;
                    case VolleyErrorHelper.NOT_FOUND: // not active deal
//                        message = new JsonParser().parse(response)
//                                .getAsJsonObject()
//                                .get("error")
//                                .getAsJsonObject()
//                                .get("message")
//                                .getAsString();
                        message = "The deal is not available for redemption.";

                        showResult(false, message);
                        break;
                    case VolleyErrorHelper.FORBIDDEN:
                        message = new JsonParser().parse(response)
                                .getAsJsonObject()
                                .get("error")
                                .getAsJsonObject()
                                .get("message")
                                .getAsString();

                        showResult(false, message);
                        break;
                    default:
                        showResult(false,"");
                        break;
                }
            }
        };

        getBurppleApi().enqueue(RedemptionRequest.load(mUserId, mDealId, mVenueId, listener), TAG);
        showLoading(true);
    }

    private void constructParams(String barcode){
//        String regex = "13_2_2015-10-14 23:00:47 +0800";
//
//        boolean validQR = barcode.matches("^[\\d+]_\\d+_");

        List<String> values = new ArrayList<>();
        Collections.addAll(values, barcode.split("_"));
        if (values.size()<2){
            return;
        } else {
            mDealId = values.get(0);
            mVenueId =values.get(1);
        }
    }

    private void showResult(boolean success, String message){
        showLoading(false);

        if (success){
            mCardContainer.setVisibility(View.VISIBLE);
            mRedeemTime.setVisibility(View.VISIBLE);
            mRedeemTitle.setText("You have successfully redeemed the deal! ^^");
            mRedeemMessage.setVisibility(View.GONE);
            mProgressAnimation.setVisibility(View.VISIBLE);
            mCountdownTimer.setVisibility(View.VISIBLE);
            mTimerLayout.setVisibility(View.VISIBLE);
        }
        else {
            mCardContainer.setVisibility(View.GONE);
            mRedeemTime.setVisibility(View.GONE);
            mRedeemTitle.setText("Sorry, you failed to redeem the deal.");
            mRedeemMessage.setVisibility(View.VISIBLE);
            mRedeemMessage.setText(message);
            mProgressAnimation.setVisibility(View.GONE);
            mCountdownTimer.setVisibility(View.GONE);
            mTimerLayout.setVisibility(View.GONE);
        }
    }

    private void showLoading(boolean loading){
        if (loading) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRedeeemCard.setVisibility(View.GONE);
            mCardContainer.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mRedeeemCard.setVisibility(View.VISIBLE);
            mCardContainer.setVisibility(View.VISIBLE);
        }
    }

    class RedemptionCountDownTimer extends CountDownTimer {
        TextView mTimer;

        public RedemptionCountDownTimer(long millisInFuture, long countDownInterval, TextView mTimer) {
            super(millisInFuture, countDownInterval);
            this.mTimer = mTimer;
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            String time = //"" + millisUntilFinished / 1000 + "s";
            String time = String.format("%01d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
            );

            mTimer.setText(time);
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(BarcodeResultActivity.this, DealsFeedActivity.class));
            finishAffinity();
        }
    }
}
