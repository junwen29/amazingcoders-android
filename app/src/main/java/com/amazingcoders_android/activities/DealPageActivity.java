package com.amazingcoders_android.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amazingcoders_android.BurppleApplication;
import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.DealRequest;
import com.amazingcoders_android.async_tasks.RegisterDealViewCountTask;
import com.amazingcoders_android.helpers.images.PicassoRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.BookmarkButton;
import com.amazingcoders_android.views.DealDetailsCard;
import com.amazingcoders_android.views.VenueCard;
import com.android.volley.VolleyError;
import com.cloudinary.Cloudinary;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DealPageActivity extends BaseActivity {

    @InjectView(R.id.btn_bookmark)
    BookmarkButton mBookmarkButton;
    @InjectView(R.id.container)
    LinearLayout mContainer;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.card_deal)
    DealDetailsCard mDealCard;
    @InjectView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.image)
    ImageView mCoverImage;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.deal_layout)
    LinearLayout mDealLayout;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    private Deal mDeal;
    private Long mDealId;
    private boolean mFeatured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_page);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDealId = getIntent().getLongExtra("deal_id", 0);

        // register deal view count by merchant push notification
        mFeatured = getIntent().getBooleanExtra("featured", false);
        if (mFeatured && mDealId != null) {
            //register view count
            RegisterDealViewCountTask registerDealViewCountTask = new RegisterDealViewCountTask(this, "merchant_push_notification", mDealId);
            registerDealViewCountTask.execute(null, null, null);
        }

        loadDeal(mDealId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deal_page, menu);
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

    public void loadDeal(Long id) {
        Listener<Deal> listener = new Listener<Deal>() {
            @Override
            public void onResponse(Deal deal) {
                mDeal = deal;
                showRefreshing(false);

                mBookmarkButton.setDeal(mDeal);
                mBookmarkButton.setVisibility(View.VISIBLE);
                mDealCard.update(mDeal);
                mCollapsingToolbarLayout.setTitle(mDeal.getTitle());
                updateCoverImage();

                // display deal's venues
                List<Venue> venues = mDeal.getVenues();
                for (final Venue venue : venues) {
                    int index = 0;
                    VenueCard venueCard = new VenueCard(DealPageActivity.this);
                    venueCard.update(venue);
                    venueCard.getmName().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DealPageActivity.this, VenuePageActivity.class);
                            intent.putExtra("id", venue.id);
                            startActivity(intent);
                        }
                    });
                    mContainer.addView(venueCard);
                    index++;
                    if (index < venues.size()) {
                        View divider = new View(DealPageActivity.this);
                        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                        divider.setBackgroundColor(getResources().getColor(R.color.light_gray));
                        mContainer.addView(divider);
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("Load Deal Error", volleyError.getMessage());
                showRefreshing(false);
            }
        };
        BurppleApi.getInstance(this).enqueue(DealRequest.load(id, listener));
        showRefreshing(true);
    }

    private void updateCoverImage(){
        Cloudinary cloudinary = BurppleApplication.getInstance(this).getCloudinary();
        String url = cloudinary.url().generate(mDeal.getImageUrl());
        PicassoRequest.get(this, url, R.drawable.img_deal_placeholder).into(mCoverImage);
    }

    private void showRefreshing(final boolean show){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mDealLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mDealLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mDealLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mAppBarLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mAppBarLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAppBarLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mDealLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mAppBarLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

