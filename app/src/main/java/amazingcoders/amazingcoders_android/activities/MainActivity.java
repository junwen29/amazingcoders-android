package amazingcoders.amazingcoders_android.activities;

import android.os.Bundle;

import amazingcoders.amazingcoders_android.R;
import amazingcoders.amazingcoders_android.activities.base.NavDrawerActivity;

public class MainActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        direct to deals feed
        onNavigationItemSelected(mNavigationView.getMenu().findItem(R.id.navigation_item_5));
        finish();
    }

    @Override
    public void setupSupportActionBar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setActiveDrawerItem() {
    }


}
