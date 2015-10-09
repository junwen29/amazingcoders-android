package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.DealCard;
import com.amazingcoders_android.views.VenueDetailsCard;
import com.amazingcoders_android.views.WishButton;
import com.android.volley.VolleyError;

import java.util.Collection;

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

    private Venue mVenue;
    private Long mVenueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_venue_page);
        setContentView(R.layout.activity_venue_page);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVenueId = getIntent().getLongExtra("id", (long) 0);
        //Log.w("", "This.id = " + this.id.toString());
        loadVenue();
        loadVenueDeals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_venue_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadVenue() {
        Listener<Venue> listener = new Listener<Venue>() {
            @Override
            public void onResponse(Venue venue) {
                mVenue = venue;
                mWishButton.setVenue(mVenue);
                mWishButton.setVisibility(View.VISIBLE);
                mVenueCard.update(mVenue);
                mCollapsingToolbarLayout.setTitle(mVenue.getName());

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
        BurppleApi.getInstance(this).enqueue(VenueRequest.load(mVenueId, listener));
    }

    public void loadVenueDeals() {
        CollectionListener<Deal> listener = new CollectionListener<Deal>() {
            @Override
            public void onResponse(Collection<Deal> deals) {
                if (deals.isEmpty())
                    mContainer.setVisibility(View.GONE);
                else {
                    mContainer.setVisibility(View.VISIBLE);
                    int index = 0;
                    for (final Deal deal: deals){
                        DealCard dealCard = new DealCard(VenuePageActivity.this);
                        dealCard.update(deal);
                        dealCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(VenuePageActivity.this, DealPageActivity.class);
                                intent.putExtra("deal_id", deal.id);
                                startActivity(intent);
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

            }
        };
        BurppleApi.getInstance(this).enqueue(VenueRequest.loadDeals(mVenueId, listener));
    }
}
