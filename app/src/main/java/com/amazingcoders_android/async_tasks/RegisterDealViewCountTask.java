package com.amazingcoders_android.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.DealViewCountRequest;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.android.volley.VolleyError;

/**
 * Created by junwen29 on 10/9/2015.
 */
public class RegisterDealViewCountTask extends AsyncTask <Void,Void,Void> {
    private static final String TAG = "RegisterDealViewCount";
    BurppleApi mApi;
    Long mDealId;
    String mEntryPoint;

    public RegisterDealViewCountTask(Context context,String entry, Long dealId) {
        mApi = BurppleApi.getInstance(context);
        mDealId = dealId;
        mEntryPoint = entry;
    }

    @Override
    protected Void doInBackground(Void... params) {
        registerDealViewCount();
        Log.d(TAG, mEntryPoint + ": Registering deal view count");
        return null;
    }

    private void registerDealViewCount(){
        EmptyListener emptyListener = new EmptyListener() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.d(TAG, "Failed to register deal view count", volleyError);
            }
        };

        mApi.enqueue(DealViewCountRequest.register(mEntryPoint, mDealId, emptyListener));
    }
}
