package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.NavDrawerActivity;
import com.amazingcoders_android.adapters.FeedbackAdapter;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.FeedbackRequest;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Feedback;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewFeedbackActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener {
    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FeedbackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_view_feedback, mContainer, true);
        ButterKnife.inject(this, v);
        setup();
        loadFeedbacks();
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
        mSelectedDrawerItemId = R.id.navigation_sub_item_2;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_feedback, menu);
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
        mAdapter = new FeedbackAdapter(this, 0);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setAutoLoadListener(this);
    }

    private void loadFeedbacks() {
        CollectionListener<Feedback> listener = new CollectionListener<Feedback>() {
            @Override
            public void onResponse(Collection<Feedback> feedbacks) {
                mAdapter.clear();
                mAdapter.addAll(feedbacks);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
        String userId = Long.toString(Global.with(this).getOwnerId());
        getBurppleApi().enqueue(FeedbackRequest.loadAll(userId, listener));
    }

    @Override
    public void onLoad() {
        loadFeedbacks();
    }

    public void newFeedback(View v) {
        startActivity(new Intent(this, FeedbackActivity.class));
    }
}
