package com.amazingcoders_android.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.api.requests.GiftRequest;

/**
 * Created by Yesha on 11/3/2015.
 */
public class RedeemGiftTask extends AsyncTask<Void, Void, Void> {

    BurppleApi mApi;
    Long gift_id;
    EmptyListener mListener;

    private static final String TAG = "RedeemGiftTask";

    public RedeemGiftTask(Context context, Long gift_id, EmptyListener listener) {
        mApi = BurppleApi.getInstance(context);
        this.gift_id = gift_id;
        this.mListener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        sendFeedback();
        return null;
    }

    private void sendFeedback(){
        mApi.enqueue(GiftRequest.redeem(gift_id, mListener));
    }
}
