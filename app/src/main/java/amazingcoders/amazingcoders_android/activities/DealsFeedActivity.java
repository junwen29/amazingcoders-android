package amazingcoders.amazingcoders_android.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.Collection;

import amazingcoders.amazingcoders_android.R;
import amazingcoders.amazingcoders_android.activities.base.NavDrawerActivity;
import amazingcoders.amazingcoders_android.adapters.DealAdapter;
import amazingcoders.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import amazingcoders.amazingcoders_android.api.CollectionListener;
import amazingcoders.amazingcoders_android.api.requests.DealRequest;
import amazingcoders.amazingcoders_android.models.Deal;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DealsFeedActivity extends NavDrawerActivity implements ArrayAutoLoadAdapter.AutoLoadListener {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    DealAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //do not set content view
        View v = getLayoutInflater().inflate(R.layout.activity_deals_feed, mContainer, true);
        ButterKnife.inject(this, v);

        setup();
        loadDeals();
    }

    @Override
    public void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().getItem(4).setChecked(true);
        mSelectedDrawerItemId = R.id.navigation_item_5;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deals_feed, menu);
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
        mAdapter = new DealAdapter(this, 0);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setAutoLoadListener(this);
    }

    private void loadDeals(){
        CollectionListener<Deal> listener = new CollectionListener<Deal>() {
            @Override
            public void onResponse(Collection<Deal> deals) {
                mAdapter.addAll(deals);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showErrorMessage();
            }
        };
        getBurppleApi().enqueue(DealRequest.allDeals(listener));
    }

    @Override
    public void onLoad() {
        loadDeals();
    }
}
