package com.amazingcoders_android.fragments.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.DealAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/25/2015.
 * This abstract fragment is for the deals feed fragments in DealsFeedActivity
 */
public abstract class DealFragment extends BaseFragment implements ArrayAutoLoadAdapter.AutoLoadListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeLayout;
    @InjectView(R.id.recyclerview)
    protected RecyclerView mRecyclerView;

    protected RecyclerView.LayoutManager mLayoutManager;
    protected DealAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new DealAdapter(getContext(), 0);
        mAdapter.setAutoLoadListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_deal, container, false);
        ButterKnife.inject(this, v);

        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        initSwipeRefreshLayout();
        loadDeals();

        return  v;
    }

    private void initSwipeRefreshLayout(){
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
    }

    @Override
    public void onLoad() {
        loadDeals();
    }

    @Override
    public void onRefresh() {
        loadDeals();
    }

    public abstract void loadDeals();

    public DealAdapter getAdapter() {
        return mAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
