package com.amazingcoders_android.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.AnalyticsRequest;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.android.volley.VolleyError;

/**
 * Created by junwen29 on 10/20/2015.
 */
public class RegisterRedemptionTask extends AsyncTask<Void,Void,Void> {
    private static final String TAG = "RegisterRedemptionTask";
    BurppleApi mApi;
    String mDealId;

    public RegisterRedemptionTask(Context context,String mDealId) {
        this.mDealId = mDealId;
        mApi = BurppleApi.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        registerRedemption();
        Log.d(TAG, "Registering redemption view count");
        return null;
    }

    private void registerRedemption() {
        EmptyListener emptyListener = new EmptyListener() {
            @Override
            public void onResponse() {
                Log.d(TAG, "Registered redemption");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.d(TAG, "Failed to register deal view count", volleyError);
            }
        };

        mApi.enqueue(AnalyticsRequest.registerRedemption(mDealId, emptyListener));
    }
}
