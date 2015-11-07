package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.RedemptionAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.RedemptionRequest;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Redemption;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyRedemptionsActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;

    private RecyclerView.LayoutManager mLayoutManager;
    private RedemptionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_my_redemptions, mContainer, true);
        ButterKnife.inject(this,v);
//        initDrawerToggle(toolbar);
        init();
        loadRedemptions(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_redemptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notifications) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                mDrawerLayout.closeDrawer(Gravity.LEFT);

            mDrawerLayout.openDrawer(Gravity.RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        getSupportActionBar().setTitle("My Redemptions");
        mAdapter = new RedemptionAdapter(this, 0);
        mAdapter.setAutoLoadListener(this);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setActiveDrawerItem() {
        mNavigationView.getMenu().getItem(3).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_sub_item_2;
    }

    private void loadRedemptions(final boolean refresh){
        CollectionListener<Redemption> listener = new CollectionListener<Redemption>() {
            @Override
            public void onResponse(Collection<Redemption> redemptions) {
                mSwipeLayout.setRefreshing(false);

                if (refresh)
                    mAdapter.clear();

                mAdapter.addAll(redemptions);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mSwipeLayout.setRefreshing(false);
                showErrorMessage();
            }
        };

        mSwipeLayout.setRefreshing(true);
        String userId = Long.toString(Global.with(this).getOwnerId());
        getBurppleApi().enqueue(RedemptionRequest.loadAll(userId,listener));
    }

    @Override
    public void onLoad() {
    // nil for now
    }

    @Override
    public void onRefresh() {
        loadRedemptions(true);
    }
}
