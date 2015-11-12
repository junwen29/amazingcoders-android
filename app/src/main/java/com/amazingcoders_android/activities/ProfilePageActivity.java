package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.UserPointAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.GiftRequest;
import com.amazingcoders_android.api.requests.LoginRequest;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.UserPoint;
import com.amazingcoders_android.models.Owner;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfilePageActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener {
    TextView mOwnerUsername;
    TextView mOwnerEmail;
    TextView mOwnerName;
    TextView mOwnerBurps;
    @InjectView(R.id.recyclerview_user_point)
    RecyclerView mRecyclerViewGift;
    RecyclerView.LayoutManager mLayoutManager;
    UserPointAdapter mAdapterGift;

    Owner mOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_profile_page, mContainer, true);
        ButterKnife.inject(this, v);
        setup();
        loadProfile();
        loadGiftRedemptions();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_page, menu);
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

    private void setup(){
        mAdapterGift = new UserPointAdapter(this, 0);
        mRecyclerViewGift.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerViewGift.setAdapter(mAdapterGift);
        mAdapterGift.setAutoLoadListener(this);
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
        mNavigationView.getMenu().getItem(2).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_sub_item_1;
    }

    private void loadProfile() {

        mOwnerUsername = (TextView) findViewById(R.id.display_username);
        mOwnerEmail = (TextView) findViewById(R.id.display_email);
        mOwnerName = (TextView) findViewById(R.id.display_name);
        mOwnerBurps = (TextView) findViewById(R.id.display_burps);
        mOwner = Global.with(this).getOwner();

        Listener<Owner> listener = new Listener<Owner>() {
            @Override
            public void onResponse(Owner owner) {
                Global.with(ProfilePageActivity.this).updateOwner(owner);
                mOwner = owner;
                mOwnerUsername.setText(mOwner.getUsername());
                mOwnerEmail.setText(mOwner.getEmail());
                mOwnerName.setText(mOwner.getFullName());
                mOwnerBurps.setText(String.valueOf(mOwner.getBurps()));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
        BurppleApi.getInstance(this).enqueue(LoginRequest.getOwner(mOwner.getUsername(), listener));
    }

    public void viewGifts(View v) {
        startActivity(new Intent(this, GiftActivity.class));
    }

    private void loadGiftRedemptions(){
        CollectionListener<UserPoint> listener = new CollectionListener<UserPoint>() {
            @Override
            public void onResponse(Collection<UserPoint> redemptions) {
                mAdapterGift.clear();
                mAdapterGift.addAll(redemptions);
                mAdapterGift.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
        String userId = Long.toString(Global.with(this).getOwnerId());
        getBurppleApi().enqueue(GiftRequest.loadAll(userId, listener));
    }

    @Override
    public void onLoad() {
        loadGiftRedemptions();
    }
}
