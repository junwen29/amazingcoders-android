package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.RedemptionRequest;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Redemption;
import com.amazingcoders_android.views.DealDetailsCard;
import com.amazingcoders_android.views.VenueDetailsCard;
import com.android.volley.VolleyError;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BarcodeResultActivity extends BaseActivity {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeResultActivity";

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.value)
    TextView mValue;
    @InjectView(R.id.barcode_container)
    LinearLayout mContainer;
    @InjectView(R.id.card_deal)
    DealDetailsCard mDealCard;
    @InjectView(R.id.card_venue)
    VenueDetailsCard mVenueCard;
    @InjectView(R.id.redeem_time)
    TextView mRedeemTime;

    private String mUserId, mDealId, mVenueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_result);

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    Snackbar.make(mContainer, R.string.barcode_success,
                            Snackbar.LENGTH_LONG)
                            .show();

                    mValue.setText(barcode.displayValue);

                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    constructParms(barcode.displayValue);
                    loadRedemption();

                } else {
//                    mValue.setText(R.string.barcode_failure);
                    Snackbar.make(mContainer, R.string.barcode_failure,
                            Snackbar.LENGTH_LONG)
                            .show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Snackbar.make(mContainer, R.string.barcode_error,
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadRedemption(){
        if (TextUtils.isEmpty(mDealId) ||TextUtils.isEmpty(mVenueId)){
            return;
        }

        mUserId = Long.toString(Global.with(this).getOwnerId());

        Listener<Redemption> listener = new Listener<Redemption>() {
            @Override
            public void onResponse(Redemption redemption) {
                //TODO convert date time to user friendly text
                mRedeemTime.setText(redemption.getCreatedAt());
                mDealCard.update(redemption.getDeal());
                mVenueCard.update(redemption.getVenue());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, volleyError.getMessage());
            }
        };

        getBurppleApi().enqueue(RedemptionRequest.load(mUserId, mDealId, mVenueId, listener), TAG);
    }

    private void constructParms(String barcode){
        List<String> values = new ArrayList<>();
        Collections.addAll(values, barcode.split("_"));
        mDealId = values.get(0);
        mVenueId =values.get(1);
    }
}
