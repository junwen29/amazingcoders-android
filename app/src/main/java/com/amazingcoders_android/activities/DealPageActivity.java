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
import com.amazingcoders_android.api.requests.DealRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.BookmarkButton;
import com.amazingcoders_android.views.DealDetailsCard;
import com.amazingcoders_android.views.VenueCard;
import com.android.volley.VolleyError;

import java.util.Collection;

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

    private Deal mDeal;
    private Long mDealId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_page_new);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDealId = getIntent().getLongExtra("deal_id",0);
        loadDeal(mDealId);
        loadDealVenues();
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

    public void loadDeal(Long id){
        Listener<Deal> listener = new Listener<Deal>() {
            @Override
            public void onResponse(Deal deal)  {
                mDeal = deal;
                mBookmarkButton.setDeal(mDeal);
                mBookmarkButton.setVisibility(View.VISIBLE);
                mDealCard.update(mDeal);
                mCollapsingToolbarLayout.setTitle(mDeal.getTitle());

//                TextView title = (TextView) findViewById(R.id.dealTitle);
//                title.setText(deal.getTitle());
//                //TextView type = (TextView) findViewById(R.id.type);
//                //type.setText(deal.getType());
//                TextView description = (TextView) findViewById(R.id.description);
//                description.setText("Description: " + deal.getDescription());
//                TextView start_date = (TextView) findViewById(R.id.start_date);
//                start_date.setText("Start Date: " + deal.getStart());
//                TextView end_date = (TextView) findViewById(R.id.end_date);
//                end_date.setText("Expiry Date: " + deal.getExpiry());
//                TextView terms = (TextView) findViewById(R.id.terms);
//                terms.setText("Term and Conditions: " + deal.getTerms());
//                TextView offeredAtTV = (TextView) findViewById(R.id.offeredAt);
//                offeredAtTV.setText("Offered at:");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("Load Deal Error", volleyError.getMessage());
            }
        };
        BurppleApi.getInstance(this).enqueue(DealRequest.load(id, listener));
    }

    public void loadDealVenues() {
        CollectionListener<Venue> listener = new CollectionListener<Venue>() {
            @Override
            public void onResponse(Collection<Venue> venues) {

                for (final Venue venue : venues){
                    int index = 0;
                    VenueCard venueCard = new VenueCard(DealPageActivity.this);
                    venueCard.update(venue);
                    venueCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DealPageActivity.this, VenuePageActivity.class);
                            intent.putExtra("id", venue.id);
                            startActivity(intent);
                        }
                    });
                    mContainer.addView(venueCard);
                    index++;
                    if (index < venues.size()){
                        View divider = new View(DealPageActivity.this);
                        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                        divider.setBackgroundColor(getResources().getColor(R.color.light_gray));
                        mContainer.addView(divider);
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
        BurppleApi.getInstance(this).enqueue(DealRequest.loadVenues(mDealId, listener));
    }
}
