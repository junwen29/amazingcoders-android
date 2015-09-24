package com.amazingcoders_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;
import com.amazingcoders_android.fragments.base.BaseFragment;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class PopularDealsFragment extends BaseFragment {

    public PopularDealsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular_deals, container, false);

    }
}
