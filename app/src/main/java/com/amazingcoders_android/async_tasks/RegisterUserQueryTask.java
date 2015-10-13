package com.amazingcoders_android.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.api.requests.UserQueryRequest;
import com.android.volley.VolleyError;

/**
 * Created by junwen29 on 10/10/2015.
 */
public class RegisterUserQueryTask extends AsyncTask<Void,Void,Void> {
    private static final String TAG = "RegisterUserQueryTask";
    BurppleApi mApi;
    String mType;
    String mQuery;

    public RegisterUserQueryTask(Context context, String mType, String mQuery) {
        mApi = BurppleApi.getInstance(context);
        this.mType = mType;
        this.mQuery = mQuery;
    }

    @Override
    protected Void doInBackground(Void... params) {
        registerQuery();
        Log.d(TAG, "Registering user query: type=" + mType + " query=" + mQuery );
        return null;
    }

    private void registerQuery() {
        EmptyListener emptyListener = new EmptyListener() {
            @Override
            public void onResponse() {
                Log.d(TAG, "Registered user query");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Log.d(TAG, "Failed to register user query", volleyError);
            }
        };
        mApi.enqueue(UserQueryRequest.register(mQuery, mType, emptyListener));
    }
}
