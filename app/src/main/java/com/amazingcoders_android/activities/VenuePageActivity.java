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
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.async_tasks.RegisterDealViewCountTask;
import com.amazingcoders_android.helpers.Helper;
import com.amazingcoders_android.helpers.images.PicassoRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.DealCard;
import com.amazingcoders_android.views.VenueDetailsCard;
import com.amazingcoders_android.views.WishButton;
import com.android.volley.VolleyError;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenuePageActivity extends BaseActivity {

    @InjectView(R.id.btn_wish)
    WishButton mWishButton;
    @InjectView(R.id.container)
    LinearLayout mContainer;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.card_venue)
    VenueDetailsCard mVenueCard;
    @InjectView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.image)
    ImageView mCoverImage;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.venue_layout)
    LinearLayout mVenueLayout;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    private Venue mVenue;
    private Long mVenueId;

    private static final String TAG = "VenuePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_page);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVenueId = getIntent().getLongExtra("id", (long) 0);
        loadVenue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_venue_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void loadVenue() {
        Listener<Venue> listener = new Listener<Venue>() {
            @Override
            public void onResponse(Venue venue) {
                mVenue = venue;
                showRefreshing(false);

                mWishButton.setVenue(mVenue);
                mWishButton.setVisibility(View.VISIBLE);
                mVenueCard.update(mVenue);
                mCollapsingToolbarLayout.setTitle(mVenue.getName());
                updateCoverImage();

                List<Deal> deals = venue.getDeals();
                if (deals == null || deals.isEmpty() )
                    mContainer.setVisibility(View.GONE);
                else {
                    mContainer.setVisibility(View.VISIBLE);
                    int index = 0;
                    for (final Deal deal: deals){
                        DealCard dealCard = new DealCard(VenuePageActivity.this);
                        dealCard.update(deal);
                        dealCard.getmTitle().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(VenuePageActivity.this, DealPageActivity.class);
                                intent.putExtra("deal_id", deal.id);
                                startActivity(intent);

                                //register view count
                                RegisterDealViewCountTask registerDealViewCountTask = new RegisterDealViewCountTask(VenuePageActivity.this, TAG, deal.id);
                                registerDealViewCountTask.execute(null,null,null);
                            }
                        });
                        mContainer.addView(dealCard);
                        index++;
                        if (index < deals.size()){
                            View divider = new View(VenuePageActivity.this);
                            divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                            divider.setBackgroundColor(getResources().getColor(R.color.light_gray));
                            mContainer.addView(divider);
                        }
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showRefreshing(false);
            }
        };
        BurppleApi.getInstance(this).enqueue(VenueRequest.load(mVenueId, listener));
        showRefreshing(true);
    }

    private void updateCoverImage(){
        Cloudinary cloudinary = BurppleApplication.getInstance(this).getCloudinary();
        String url = cloudinary.url().generate(mVenue.getPhotoUrl());
        PicassoRequest.get(this, url, R.drawable.img_venue_placeholder).into(mCoverImage);
    }

    private void showRefreshing(final boolean show){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mVenueLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mVenueLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mVenueLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mVenueLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mAppBarLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
