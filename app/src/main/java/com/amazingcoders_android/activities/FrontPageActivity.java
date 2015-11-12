package com.amazingcoders_android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.dialogs.ProgressDialogFactory;
import com.amazingcoders_android.helpers.PreferencesStore;
import com.amazingcoders_android.helpers.images.PicassoRequest;
import com.amazingcoders_android.services.UpdateServerService;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FrontPageActivity extends BaseActivity {

    private static final int REQ_SIGNUP = 4001;
    private static final int REQ_VERIFY_ACCOUNT = 4002;
    private static final int REQ_LOGIN = 4005;

    public enum SessionState {
        LOGGED_OUT,
        LOGGED_IN,
    }

    // GCM
    private GoogleCloudMessaging mGCM;
    private String mRegId;
    private static final String GCM_SENDER_ID = "409592313883"; //Google API Project Number

    @InjectView(R.id.pager)
    ViewPager mPager;

    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            mPager.setCurrentItem((mPager.getCurrentItem() + 1) % 3, true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionState sessionState = getSessionState();
        if (sessionState == SessionState.LOGGED_IN) {
            goToMain();
        }
        else { // Show frontpage
            setContentView(R.layout.activity_front_page);
            ButterKnife.inject(this);

            setProgressDialog(ProgressDialogFactory.create(this, R.string.progress_loading));

            boolean networkAvailable = isNetworkAvailable();
            if (!networkAvailable) { // Do not load carousel image, instead show static placeholder image
                ImageView placeholder = (ImageView) findViewById(R.id.image_placeholder);
                placeholder.setVisibility(View.VISIBLE);
                placeholder.setImageResource(R.drawable.img_frontpage);
            }
            mPager.setOffscreenPageLimit(3);
            mPager.setAdapter(new ImagePagerAdapter(this, networkAvailable));

            // Hack to auto scroll images
            Class<?> viewpager = ViewPager.class;
            try {
                Field scroller = viewpager.getDeclaredField("mScroller");
                scroller.setAccessible(true);
                scroller.set(mPager, new CarouselScroller(this));
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_front_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        startActivity(new Intent(this, VenuesFeedActivity.class));
        finish();
    }

    private SessionState getSessionState() {
        PreferencesStore pm = new PreferencesStore(this);
        if (pm.getAuthToken() == null) {
            return SessionState.LOGGED_OUT;
        }
        else {
            return SessionState.LOGGED_IN;
        }
    }

    @OnClick(R.id.button_login)
    public void onLoginClick() {
        startActivityForResult(new Intent(this, LoginActivity.class), REQ_LOGIN);
    }

    @OnClick(R.id.button_signup)
    public void onSignupClick() {
        startActivityForResult(
                new Intent(this, SignupActivity.class).setAction(SignupActivity.SIGNUP_EMAIL),
                REQ_SIGNUP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQ_SIGNUP:
                    startActivity(new Intent(this, VenuesFeedActivity.class));
                    Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case REQ_LOGIN:
                    startActivity(new Intent(this, VenuesFeedActivity.class));
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    //do nothing
            }
            //register GCM no matter login or signup
            registerGCM();
        }
    }

    private static class ImagePagerAdapter extends PagerAdapter {
        // NOTE count == 3
        private Context mContext;
        private int[] mTitleIds;
        private int[] mSubtitleIds;
        private String[] mUrls;
        private boolean mHasNetwork;

        public ImagePagerAdapter(Context context, boolean hasNetwork) {
            mHasNetwork = hasNetwork;
            mContext = context;
            mTitleIds = new int[]{R.string.copy_frontpage_1, R.string.copy_frontpage_2, R.string.copy_frontpage_3};
            mSubtitleIds = new int[]{
                    R.string.copy_frontpage_1_sub, R.string.copy_frontpage_2_sub, R.string.copy_frontpage_3_sub
            };
            mUrls = new String[]{
                    "https://s3.amazonaws.com/burpple-production/assets/images/edu/image1.jpg",
                    "https://s3.amazonaws.com/burpple-production/assets/images/edu/image2.jpg",
                    "https://s3.amazonaws.com/burpple-production/assets/images/edu/image3.jpg"
            };
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.frontpage, container, false);

            ImageView iv = (ImageView) v.findViewById(R.id.image);
            if (mHasNetwork) {
                if (position == 0) {
                    iv.setImageResource(R.drawable.img_frontpage);
                } else {
                    PicassoRequest.get(mContext, mUrls[position]).into(iv);
                }
            }
            ((TextView) v.findViewById(R.id.text_title)).setText(mTitleIds[position]);
            ((TextView) v.findViewById(R.id.text_subtitle)).setText(mSubtitleIds[position]);

            container.addView(v);
            return v;
        }
    }

    private class CarouselScroller extends Scroller {
        public CarouselScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 500);
        }
    }

    private void registerGCM(){
        mGCM = GoogleCloudMessaging.getInstance(this);
        mRegId = getGCMRegId();
        if (mRegId.isEmpty()){
            registerGCMInBackground();
        }
    }

    private void registerGCMInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                Context context = getApplicationContext();
                try{
                    if (mGCM == null){
                        mGCM = GoogleCloudMessaging.getInstance(context);
                    }
                    mRegId = mGCM.register(GCM_SENDER_ID);
                    msg = "Device registered, registration ID= "+ mRegId;

                    startService(UpdateServerService.deviceToken(context, true, mRegId));
                    // Persist the regID - no need to register again.
                    PreferencesStore store = new PreferencesStore(context);
                    store.storeRegistrationId(context, mRegId);

                    Log.i("GCM", msg);
                } catch (IOException e) {
                    msg = "Error: " + e.getMessage();
                    e.printStackTrace();
                }

                return msg;
            }
        }.execute(null, null, null);
    }

    private String getGCMRegId(){
        PreferencesStore ps = new PreferencesStore(getApplicationContext());
        String registrationId = ps.getGCMRegistrationId();
        if (registrationId.isEmpty()){
            Log.d("getGCMRegID", "GCM Registration ID not found.");
            return "";
        }
        return registrationId;
    }
}
