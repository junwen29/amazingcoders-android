package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazingcoders_android.helpers.AmazingHelper;
import com.android.volley.VolleyError;

import java.util.Collection;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.DealAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.DealRequest;
import com.amazingcoders_android.models.Deal;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DealsFeedActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;
    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    DealAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //do not set content view
        View v = getLayoutInflater().inflate(R.layout.activity_deals_feed, mContainer, true);
        ButterKnife.inject(this, v);

        setup();
        loadDeals();
    }

    @Override
    public void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().getItem(4).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_item_5;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deals_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setup(){
        mAdapter = new DealAdapter(this, 0);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setAutoLoadListener(this);

        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout(){
        int offset = 256;
        //calculate the action bar size and determine the end
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            offset = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            offset = offset * 3 / 2;
        }
        mSwipeLayout.setProgressViewOffset(true, 0, offset);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
    }

    private void loadDeals(){
        CollectionListener<Deal> listener = new CollectionListener<Deal>() {
            @Override
            public void onResponse(Collection<Deal> deals) {
                mSwipeLayout.setRefreshing(false);
                mAdapter.addAll(deals);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mSwipeLayout.setRefreshing(false);
                showErrorMessage();
            }
        };
        mSwipeLayout.setRefreshing(true);
        getBurppleApi().enqueue(DealRequest.allDeals(listener));
    }

    @Override
    public void onLoad() {
        loadDeals();
    }

    @Override
    public void onRefresh() {
        loadDeals();
    }
}
