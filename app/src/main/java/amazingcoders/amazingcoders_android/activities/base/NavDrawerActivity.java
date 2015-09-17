package amazingcoders.amazingcoders_android.activities.base;

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

import amazingcoders.amazingcoders_android.R;
import amazingcoders.amazingcoders_android.activities.DealsFeedActivity;

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

                case R.id.navigation_item_1:
                case R.id.navigation_item_2:
                case R.id.navigation_item_3:
                case R.id.navigation_item_4:
                case R.id.navigation_sub_item_1:
                case R.id.navigation_sub_item_2:
                    break;
                case R.id.navigation_item_5:
                    startActivity(new Intent(this, DealsFeedActivity.class));
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
}
