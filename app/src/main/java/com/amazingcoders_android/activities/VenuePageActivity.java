package com.amazingcoders_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.DealCard;
import com.amazingcoders_android.views.WishButton;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenuePageActivity extends Activity {

    @InjectView(R.id.btn_wish)
    WishButton mWishButton;
    @InjectView(R.id.container)
    LinearLayout mContainer;

    private Venue mVenue;
    private Long mVenueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_page);
        ButterKnife.inject(this);

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

                TextView name = (TextView) findViewById(R.id.nameTV);
                name.setText(venue.getName());
                TextView street = (TextView) findViewById(R.id.streetTV);
                street.setText("Address: " + venue.getStreet());
                TextView zipcode = (TextView) findViewById(R.id.zipcodeTV);
                zipcode.setText("Postal Code: " + venue.getZipcode());
                TextView bio = (TextView) findViewById(R.id.bioTV);
                bio.setText("Description: " + venue.getBio());
                TextView neighbourhood = (TextView) findViewById(R.id.neighbourhoodTV);
                neighbourhood.setText("Neighbourhood: " + venue.getNeighbourhood());
                TextView phone = (TextView) findViewById(R.id.phoneTV);
                phone.setText("Contact Number: " + venue.getPhone());
                //TextView contact = (TextView) findViewById(R.id.contact_numberTV);
                //contact.setText("Contact Number: " + venue.getContact_number());
                TextView dealsOfferedTV = (TextView) findViewById(R.id.dealsOffered);
                dealsOfferedTV.setText("Deals Offered:");
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

                for (final Deal deal : deals){
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
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
        BurppleApi.getInstance(this).enqueue(VenueRequest.loadDeals(mVenueId, listener));
    }
}
