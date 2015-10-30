package com.amazingcoders_android.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.api.requests.FeedbackRequest;
import com.amazingcoders_android.helpers.Global;

/**
 * Created by junwen29 on 10/29/2015.
 */
public class SendFeedbackTask extends AsyncTask<Void,Void,Void> {

    BurppleApi mApi;
    String mTitle;
    String mCategory;
    String mDesc;
    String mUserId;
    EmptyListener mListener;

    private static final String TAG = "SendFeedbackTask";

    public SendFeedbackTask(Context context, String mTitle, String mCategory, String mDesc,
                            EmptyListener listener) {
        mApi = BurppleApi.getInstance(context);
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mDesc = mDesc;
        mListener = listener;
        mUserId = Long.toString(Global.with(context).getOwnerId());
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "Sending Feedback: title=" + mTitle + " category=" + mCategory + " Desc= " + mDesc);
        sendFeedback();
        return null;
    }

    private void sendFeedback(){
        mApi.enqueue(FeedbackRequest.send(mTitle, mUserId, mCategory, mDesc, mListener));
    }
}
