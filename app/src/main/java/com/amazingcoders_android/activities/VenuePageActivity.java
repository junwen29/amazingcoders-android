package com.amazingcoders_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.DealAdapter;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.DealCard;
import com.android.volley.VolleyError;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenuePageActivity extends Activity {
    Long id;

//    DealAdapter mAdapter;
//    RecyclerView mRecyclerView;

    @InjectView(R.id.container)
    LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_page);
        this.id = getIntent().getLongExtra("id", new Long(0));

        ButterKnife.inject(this);
        //Log.w("", "This.id = " + this.id.toString());
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(llm);
//        mAdapter = new DealAdapter(getApplicationContext(), 0);
//        mRecyclerView.setAdapter(mAdapter);
        loadVenue();
        loadVenueDeals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venue_page, menu);
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

    public void loadVenue() {
        Listener<Venue> listener = new Listener<Venue>() {
            @Override
            public void onResponse(Venue venue) {
                TextView name = (TextView) findViewById(R.id.nameTV);
                name.setText(venue.getName());
                TextView street = (TextView) findViewById(R.id.streetTV);
                street.setText("Street: " + venue.getStreet());
                TextView zipcode = (TextView) findViewById(R.id.zipcodeTV);
                zipcode.setText("Zipcode: " + venue.getZipcode());
                TextView bio = (TextView) findViewById(R.id.bioTV);
                bio.setText("Bio: " + venue.getBio());
                TextView neighbourhood = (TextView) findViewById(R.id.neighbourhoodTV);
                neighbourhood.setText("Neighbourhood: " + venue.getNeighbourhood());
                TextView phone = (TextView) findViewById(R.id.phoneTV);
                phone.setText("Phone: " + venue.getPhone());
                TextView contact = (TextView) findViewById(R.id.contact_numberTV);
                contact.setText("Contact Number: " + venue.getContact_number());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
        BurppleApi.getInstance(this).enqueue(VenueRequest.load(id, listener));
    }

    public void loadVenueDeals() {
        CollectionListener<Deal> listener = new CollectionListener<Deal>() {
            @Override
            public void onResponse(Collection<Deal> deals) {
//                mAdapter.addAll(deals);
//                mAdapter.notifyDataSetChanged();

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
        BurppleApi.getInstance(this).enqueue(VenueRequest.loadDeals(id, listener));
    }
}
