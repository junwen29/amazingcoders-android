package amazingcoders.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import amazingcoders.amazingcoders_android.R;
import amazingcoders.amazingcoders_android.activities.base.BaseActivity;
import amazingcoders.amazingcoders_android.activities.base.NavDrawerActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        direct to deals feed
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, FrontPageActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
