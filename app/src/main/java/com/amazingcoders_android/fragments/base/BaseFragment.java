package com.amazingcoders_android.fragments.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.BurppleApi;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

/**
 * Created by junwen29 on 9/25/2015.
 */
public abstract class BaseFragment extends Fragment {

    public void showErrorMessage(VolleyError error) {
        error.printStackTrace();
        if (error instanceof NoConnectionError) {
            showErrorMessage(R.string.error_network);
        } else {
            showErrorMessage();
        }
    }

    public void showErrorMessage() {
        showErrorMessage(R.string.error_default);
    }

    public void showErrorMessage(int resId) {
        if (getActivity() == null) return;

        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_LONG).show();
    }

    public BurppleApi getBurppleApi() {
        return ((BaseActivity) getActivity()).getBurppleApi();
    }
}
