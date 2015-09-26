package com.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.requests.DealRequest;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.views.BookmarkButton;
import com.android.volley.VolleyError;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DealPageActivity extends AppCompatActivity {

    @InjectView(R.id.btn_bookmark)
    BookmarkButton mBookmarkButton;

    private Deal mDeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_page);
        ButterKnife.inject(this);

        Long dealId = getIntent().getLongExtra("deal_id",0);
        loadDeal(dealId);
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

                TextView title = (TextView) findViewById(R.id.dealTitle);
                title.setText(deal.getTitle());
                //TextView type = (TextView) findViewById(R.id.type);
                //type.setText(deal.getType());
                TextView description = (TextView) findViewById(R.id.description);
                description.setText("Description: " + deal.getDescription());
                TextView location = (TextView) findViewById(R.id.location);
                location.setText("Location: " + deal.getLocation());
                TextView start_date = (TextView) findViewById(R.id.start_date);
                start_date.setText("Start Date: " + deal.getStart());
                TextView end_date = (TextView) findViewById(R.id.end_date);
                end_date.setText("Expiry Date: " + deal.getExpiry());
                TextView terms = (TextView) findViewById(R.id.terms);
                terms.setText("Term and Conditions: " + deal.getTerms());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("Load Deal Error", volleyError.getMessage());
            }
        };
        BurppleApi.getInstance(this).enqueue(DealRequest.load(id, listener));
    }
}
