package com.amazingcoders_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;

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
                startActivity(new Intent(MainActivity.this,VenuesFeedActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void registerGCM(){

    }
}
