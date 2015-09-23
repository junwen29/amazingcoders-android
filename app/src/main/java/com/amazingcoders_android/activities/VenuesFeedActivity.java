package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.Collection;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.VenueAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.models.Venue;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenuesFeedActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener {

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
    }

    @Override
    public void setActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().getItem(2).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_item_2;
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
    }

    @Override
    public void onLoad() {
        loadVenues();
    }

    private void loadVenues() {
        CollectionListener<Venue> listener = new CollectionListener<Venue>() {
            @Override
            public void onResponse(Collection<Venue> venues) {
                mAdapter.addAll(venues);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showErrorMessage();
            }
        };
        getBurppleApi().enqueue(VenueRequest.allVenues(listener));
    }
}
