package com.amazingcoders_android.activities.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.CreateFeedbackActivity;
import com.amazingcoders_android.activities.FrontPageActivity;
import com.amazingcoders_android.activities.DealsFeedActivity;
import com.amazingcoders_android.activities.MyRedemptionsActivity;
import com.amazingcoders_android.activities.VenuesFeedActivity;
import com.amazingcoders_android.dialogs.AlertDialogFactory;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.helpers.PreferencesStore;

/**
 * Created by junwen29 on 9/17/2015.
 */
public abstract class NavDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected FrameLayout mContainer;
    protected Toolbar mToolbar;

    protected ActionBarDrawerToggle mDrawerToggle;
    protected int mSelectedDrawerItemId = -1;
    protected boolean mShouldInitDrawerToggle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mContainer = (FrameLayout) findViewById(R.id.container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setup();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) mDrawerToggle.syncState();
    }

    private void setup() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.color_primary_dark));

        initDrawer();
        setupSupportActionBar();
        if (mShouldInitDrawerToggle)
            initDrawerToggle(mToolbar);
    }

    private void initDrawer() {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow_rev, GravityCompat.END);

        mNavigationView.setNavigationItemSelectedListener(this);
        // TODO add profile header view
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == mSelectedDrawerItemId)
            return false;
        else {
            switch (id) {
                case R.id.navigation_item_1: // Venues Feed
                    startActivity(new Intent(this, VenuesFeedActivity.class));
                    break;

                case R.id.navigation_item_2:
                    startActivity(new Intent(this, DealsFeedActivity.class));
                    break;



                case R.id.navigation_sub_item_1:
                    startActivity(new Intent(this, MyRedemptionsActivity.class));
                    break;

                case R.id.navigation_sub_item_2: // create feedback
                    startActivity(new Intent(this, CreateFeedbackActivity.class));
                    break;

                case R.id.navigation_sub_item_3: // Logout
                    showLogoutPrompt();
                    break;

                default:
                    return false;
            }
            mDrawerLayout.closeDrawer(mNavigationView);
            //update the selected drawer item id
            mSelectedDrawerItemId = id;
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setActiveDrawerItem();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else if (mDrawerLayout.isDrawerOpen(GravityCompat.END))
            mDrawerLayout.closeDrawer(GravityCompat.END);
        else
            super.onBackPressed();
    }

    protected void initDrawerToggle(Toolbar toolbar) {
        if (toolbar == null) return;

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.desc_drawer_open,
                R.string.desc_drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    /**
     * set toolbar == null if need to remove toolbar
     * use default support toolbar as action bar
     * set toolbar visibility to GONE  if need to remove to toolbar view
     */
    public abstract void setupSupportActionBar();

    /**
     * highlight selected navigation menu item
     */
    public abstract void setActiveDrawerItem();

    private void showLogoutPrompt() {
        AlertDialogFactory.logout(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }).show();
    }

    private void logout() {
        // use application context for logging out
        PreferencesStore store = new PreferencesStore(this.getApplicationContext());
        store.clearAuthToken();
        store.clear();
        Global.with(this).reset();
//        startService(UpdateServerService.deviceToken(getActivity(), false, store.getGCMRegistrationId()));

        // NOTE reset user data in MainActivity only since auth token still needed here
        store.setNotNewbie();

        Intent i = new Intent(this, FrontPageActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finishAffinity();
    }
}
