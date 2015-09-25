package com.amazingcoders_android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.VenueRequest;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.views.WishButton;
import com.android.volley.VolleyError;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenuePageActivity extends Activity {

    @InjectView(R.id.btn_wish)
    WishButton mWishButton;

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
                mVenue = venue;
                mWishButton.setVenue(mVenue);
                mWishButton.setVisibility(View.VISIBLE);

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
        BurppleApi.getInstance(this).enqueue(VenueRequest.load(mVenueId, listener));
    }
}
