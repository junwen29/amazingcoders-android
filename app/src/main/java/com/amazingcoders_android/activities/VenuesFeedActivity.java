package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.VenueAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.models.Venue;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenuesFeedActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;
    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    VenueAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getLayoutInflater().inflate(R.layout.activity_venues_feed, mContainer, true);
        ButterKnife.inject(this, v);

        setup();
        loadVenues();
    }

    @Override
    public void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void setActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().getItem(0).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_item_1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venues_feed, menu);
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
        mAdapter = new VenueAdapter(this, 0);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setAutoLoadListener(this);

        initSwipeRefreshLayout();
    }

    @Override
    public void onLoad() {
        loadVenues();
    }

    private void loadVenues() {
        CollectionListener<Venue> listener = new CollectionListener<Venue>() {
            @Override
            public void onResponse(Collection<Venue> venues) {
                mSwipeLayout.setRefreshing(false);
                mAdapter.clear();
                mAdapter.addAll(venues);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mSwipeLayout.setRefreshing(false);
                showErrorMessage();
            }
        };
        mSwipeLayout.setRefreshing(true);
        getBurppleApi().enqueue(VenueRequest.allVenues(listener));
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

    @Override
    public void onRefresh() {
        loadVenues();
    }
}
