package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.GiftAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.GiftRequest;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Gift;
import com.amazingcoders_android.models.Owner;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GiftActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    GiftAdapter mAdapter;
    TextView mOwnerBurps;
    Owner mOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getLayoutInflater().inflate(R.layout.activity_gift, mContainer, true);
        ButterKnife.inject(this, v);

        mOwner = Global.with(this).getOwner();
        mOwnerBurps = (TextView) findViewById(R.id.display_burps);
        setup();
        loadGifts();
    }

    @Override
    public void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Burpple Gifts");
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
        mAdapter = new GiftAdapter(this, 0);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setAutoLoadListener(this);
        mOwnerBurps.setText(String.valueOf(mOwner.getBurps()));
    }

    @Override
    public void onLoad() {
        loadGifts();
    }

    private void loadGifts() {
        CollectionListener<Gift> listener = new CollectionListener<Gift>() {
            @Override
            public void onResponse(Collection<Gift> gifts) {
                mAdapter.clear();
                mAdapter.addAll(gifts);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showErrorMessage();
            }
        };
        getBurppleApi().enqueue(GiftRequest.allUserGifts(listener));
    }


}
