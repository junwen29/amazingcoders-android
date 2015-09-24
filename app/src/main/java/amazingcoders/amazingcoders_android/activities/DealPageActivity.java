package amazingcoders.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;

import amazingcoders.amazingcoders_android.R;
import amazingcoders.amazingcoders_android.api.BurppleApi;
import amazingcoders.amazingcoders_android.api.Listener;
import amazingcoders.amazingcoders_android.api.requests.DealRequest;
import amazingcoders.amazingcoders_android.models.Deal;

public class DealPageActivity extends AppCompatActivity {

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_page);
        this.id = getIntent().getLongExtra("id", new Long(0));
        loadDeals();
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

    public void loadDeals(){
        Listener<Deal> listener = new Listener<Deal>() {
            @Override
            public void onResponse(Deal deal)  {
                TextView title = (TextView) findViewById(R.id.dealTitle);
                title.setText(deal.getTitle());
                TextView type = (TextView) findViewById(R.id.type);
                type.setText(deal.getType());
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(deal.getDescription());
                TextView location = (TextView) findViewById(R.id.location);
                location.setText(deal.getLocation());
                TextView start_date = (TextView) findViewById(R.id.start_date);
                start_date.setText(deal.getStart());
                TextView end_date = (TextView) findViewById(R.id.end_date);
                end_date.setText(deal.getExpiry());
                TextView terms = (TextView) findViewById(R.id.terms);
                terms.setText(deal.getTerms());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
        BurppleApi.getInstance(this).enqueue(DealRequest.load(id, listener));
    }
}
