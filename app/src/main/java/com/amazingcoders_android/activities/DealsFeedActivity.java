package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.DealAdapter;
import com.amazingcoders_android.async_tasks.RegisterUserQueryTask;
import com.amazingcoders_android.fragments.AllActiveDealsFragment;
import com.amazingcoders_android.fragments.BookmarkDealsFragment;
import com.amazingcoders_android.fragments.DiscountsDealsFragment;
import com.amazingcoders_android.fragments.FreebiesDealsFragment;
import com.amazingcoders_android.fragments.PopularDealsFragment;
import com.amazingcoders_android.fragments.base.DealFragment;
import com.amazingcoders_android.models.Deal;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class DealsFeedActivity extends NavDrawerActivity implements SearchView.OnQueryTextListener {

    @InjectView(R.id.toolbar)
    public Toolbar toolbar;
    @InjectView(R.id.tabs)
    public TabLayout tabLayout;
    @InjectView(R.id.viewpager)
    public ViewPager viewPager;
    @InjectView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getLayoutInflater().inflate(R.layout.activity_deals_feed, mContainer, true);
        ButterKnife.inject(this, v);
        initDrawerToggle(toolbar);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setupFab();

        // setup menu and search view
        toolbar.inflateMenu(R.menu.deals_feed);
        final SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void setupSupportActionBar() {
        mToolbar.setVisibility(View.GONE);
        mToolbar = null;
    }

    @Override
    public void setActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().getItem(1).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_item_5;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllActiveDealsFragment(), "All");
        adapter.addFragment(new DiscountsDealsFragment(), "Discounts");
        adapter.addFragment(new FreebiesDealsFragment(), "Freebies");
        adapter.addFragment(new PopularDealsFragment(), "Popular");
        adapter.addFragment(new BookmarkDealsFragment(), "Bookmarks");
        viewPager.setAdapter(adapter);
    }

    private void setupFab(){
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DealsFeedActivity.this, BarcodeResultActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Intent intent = new Intent(this,SearchDealActivity.class);
        intent.putExtra("query", query);
        if (!TextUtils.isEmpty(query)){
            RegisterUserQueryTask registerUserQueryTask = new RegisterUserQueryTask(this,"deal", query);
            registerUserQueryTask.execute(null,null,null);
        }
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        int currentFragmentIndex = viewPager.getCurrentItem();
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + currentFragmentIndex);
        if (page instanceof DealFragment){
            DealFragment fragment = (DealFragment) page;
            DealAdapter adapter = fragment.getAdapter();
            List<Deal> deals = adapter.getAllItems();
            final List<Deal> filteredDealList = filter(deals,newText);
            adapter.animateTo(filteredDealList);
            adapter.notifyDataSetChanged();
            fragment.getRecyclerView().scrollToPosition(0);
        }

        return true;
    }

    private List<Deal> filter(List<Deal> deals, String query) {
        query = query.toLowerCase();

        final List<Deal> filteredDealList = new ArrayList<>();
        for (Deal deal : deals) {
            final String name = deal.getTitle().toLowerCase();
            if (name.contains(query)) {
                filteredDealList.add(deal);
            }
        }

        return filteredDealList;
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
