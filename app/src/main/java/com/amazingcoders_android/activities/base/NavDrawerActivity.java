package com.amazingcoders_android.activities.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazingcoders_android.Constants;
import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.ViewFeedbackActivity;
import com.amazingcoders_android.activities.FrontPageActivity;
import com.amazingcoders_android.activities.DealsFeedActivity;
import com.amazingcoders_android.activities.MyRedemptionsActivity;
import com.amazingcoders_android.activities.ProfilePageActivity;
import com.amazingcoders_android.activities.VenuesFeedActivity;
import com.amazingcoders_android.adapters.NotificationAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.NotificationRequest;
import com.amazingcoders_android.dialogs.AlertDialogFactory;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.helpers.PreferencesStore;
import com.amazingcoders_android.models.Notification;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.InjectView;

/**
 * Created by junwen29 on 9/17/2015.
 */
public abstract class NavDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected FrameLayout mContainer;
    protected Toolbar mToolbar;
    protected RecyclerView mNotificationDrawer;
    protected NotificationAdapter mNotificationAdapter;
    protected LinearLayout mNotificationDrawerLayout;
    protected View mProgressView;
    protected TextView mEmptyView;

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
        mNotificationDrawer = (RecyclerView) findViewById(R.id.notification_drawer);
        mNotificationDrawerLayout = (LinearLayout) findViewById(R.id.notification_drawer_layout);
        mProgressView = findViewById(R.id.progress);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        setup();
        loadNotifications();
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

        mNotificationAdapter = new NotificationAdapter(this, new ArrayList<Notification>());
        mNotificationDrawer.setLayoutManager(new LinearLayoutManager(this));
        mNotificationDrawer.setAdapter(mNotificationAdapter);
    }

    private void initDrawer() {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow_rev, GravityCompat.END);

        mNavigationView.setNavigationItemSelectedListener(this);

        TextView displayUsername = (TextView) findViewById(R.id.display_header_username);
        displayUsername.setText(Global.with(this).getOwner().getUsername());

        TextView displayEmail = (TextView) findViewById(R.id.display_header_email);
        displayEmail.setText(Global.with(this).getOwner().getEmail());
    }

    private void loadNotifications(){
        CollectionListener<Notification> listener = new CollectionListener<Notification>() {
            @Override
            public void onResponse(Collection<Notification> notifications) {
                showRefreshing(false);
                mNotificationAdapter.clear();
                mNotificationAdapter.addAll(notifications);
                mNotificationAdapter.notifyDataSetChanged();

                //show empty view if no notifications
                if (notifications.isEmpty()){
                    mNotificationDrawer.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mNotificationDrawer.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showRefreshing(false);
                showErrorMessage();
            }
        };

        String userId = Long.toString(Global.with(NavDrawerActivity.this).getOwnerId());
        getBurppleApi().enqueue(NotificationRequest.loadAll(userId, listener));
        showRefreshing(true);
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

                case R.id.navigation_sub_item_1: //profile
                    startActivity(new Intent(this, ProfilePageActivity.class));
                    break;

                case R.id.navigation_sub_item_2: //redemption history
                    startActivity(new Intent(this, MyRedemptionsActivity.class));
                    break;

                case R.id.navigation_sub_item_3: // view feedback
                    startActivity(new Intent(this, ViewFeedbackActivity.class));
                    break;

                case R.id.navigation_sub_item_4: // Logout
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

            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView == mNavigationView)
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mNotificationDrawerLayout);
                else {
                    loadNotifications();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mNavigationView);
                }
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

    private void showRefreshing(final boolean show){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mNotificationDrawer.setVisibility(show ? View.GONE : View.VISIBLE);
            mNotificationDrawer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mNotificationDrawer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mNotificationDrawer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
