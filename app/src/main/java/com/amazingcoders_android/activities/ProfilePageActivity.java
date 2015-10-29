package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Owner;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfilePageActivity extends BaseActivity {
    TextView mOwnerUsername;
    TextView mOwnerEmail;
    TextView mOwnerName;
    TextView mOwnerBurps;
    TextView mOwnerWishes;
    TextView mOwnerBookmarks;
    @InjectView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    private Owner mOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        loadProfile();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_page, menu);
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

    private void loadProfile() {
        mOwnerUsername = (TextView) findViewById(R.id.display_username);
        mOwnerEmail = (TextView) findViewById(R.id.display_email);
        mOwnerName = (TextView) findViewById(R.id.display_name);
        mOwnerBurps = (TextView) findViewById(R.id.display_burps);
        mOwnerWishes = (TextView) findViewById(R.id.display_wishlisted_venues);
        mOwnerBookmarks = (TextView) findViewById(R.id.display_bookmarked_deals);

        mOwner = Global.with(this).getOwner();
        Global.with(this).updateOwner(mOwner);
        mOwner = Global.with(this).getOwner();

        mCollapsingToolbarLayout.setTitle(mOwner.getUsername());
        mOwnerUsername.setText(mOwner.getUsername());
        //Log.w("", "Owner email is " + mOwner.getEmail());
        mOwnerEmail.setText(mOwner.getEmail());
        mOwnerName.setText(mOwner.getFullName());
        //Log.w("", "Owner burps is " + mOwner.getBurps());
        mOwnerBurps.setText(String.valueOf(mOwner.getBurps()));
        mOwnerWishes.setText(String.valueOf(mOwner.getNum_wishes()));
        mOwnerBookmarks.setText(String.valueOf(mOwner.getNum_bookmarks()));
    }
}
