package com.amazingcoders_android.activities.base;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import com.amazingcoders_android.R;
import com.amazingcoders_android.api.BurppleApi;

/**
 * Created by junwen29 on 9/17/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BurppleApi mApi;
    protected String mAction;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction = getIntent().getAction();
        mApi = BurppleApi.getInstance(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
        mApi.cancel(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public void debugLog(String log) {
        Log.d(getLocalClassName(), log);
    }

    public void finishWithError() {
        showErrorMessage();
        finish();
    }

    public String getAction() {
        return mAction;
    }

    public boolean finishIfNoAction() {
        if (mAction == null) {
            showErrorMessage();
            finish();
            return true;
        }

        return false;
    }

    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    public void setProgressDialog(ProgressDialog dialog) {
        mProgressDialog = dialog;
    }

    public void showProgressDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }


    public void showErrorMessage() {
        showErrorMessage(R.string.error_default);
    }

    public void showErrorMessage(int resId) {
        showErrorMessage(getString(resId));
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showErrorMessage(VolleyError error) {
        error.printStackTrace();
        if (error instanceof NoConnectionError) {
            showErrorMessage(R.string.error_network);
        } else {
            showErrorMessage();
        }
    }

    public BurppleApi getBurppleApi() {
        if (mApi == null)
            mApi = BurppleApi.getInstance(this);
        return mApi;
    }


    /**
     * call this method to disable screenshot in QR redemption activity
     */
    protected void disableScreenShot(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }
}
