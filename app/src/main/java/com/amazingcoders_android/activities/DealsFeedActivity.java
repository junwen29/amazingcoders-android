package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.fragments.AllActiveDealsFragment;
import com.amazingcoders_android.fragments.DiscountsDealsFragment;
import com.amazingcoders_android.fragments.FreebiesDealsFragment;
import com.amazingcoders_android.fragments.PopularDealsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class DealsFeedActivity extends NavDrawerActivity{

    @InjectView(R.id.toolbar)
    public Toolbar toolbar;
    @InjectView(R.id.tabs)
    public TabLayout tabLayout;
    @InjectView(R.id.viewpager)
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getLayoutInflater().inflate(R.layout.activity_deals_feed, mContainer, true);
        ButterKnife.inject(this, v);
        initDrawerToggle(toolbar);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setupSupportActionBar() {
        mToolbar.setVisibility(View.GONE);
        mToolbar = null;
    }

    @Override
    public void setActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().getItem(5).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_item_5;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllActiveDealsFragment(), "All");
        adapter.addFragment(new DiscountsDealsFragment(), "Discounts");
        adapter.addFragment(new FreebiesDealsFragment(), "Freebies");
        adapter.addFragment(new PopularDealsFragment(), "Popular");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
