package com.amazingcoders_android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class DiscountsDealsFragment extends Fragment {
    public DiscountsDealsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discount_deals, container, false);

    }
}
