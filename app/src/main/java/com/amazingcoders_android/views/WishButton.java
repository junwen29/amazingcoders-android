package com.amazingcoders_android.views;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ToggleButton;

import com.amazingcoders_android.activities.FrontPageActivity;
import com.amazingcoders_android.api.BurppleApi;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.api.requests.WishRequest;
import com.amazingcoders_android.helpers.PreferencesStore;
import com.amazingcoders_android.models.Venue;
import com.amazingcoders_android.sync.VenueSynchronizer;
import com.android.volley.VolleyError;

import java.lang.ref.WeakReference;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class WishButton extends ToggleButton {

    static final String TAG = "WishButton";

    // checked = wished -> unwish
    // !checked = !wished -> wish

    private Venue mVenue;
    private Handler mHandler;
    private Context mContext;

    public WishButton(Context context) {
        super(context);
        init(context);
    }

    public WishButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WishButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        mHandler = new WishEventHandler(this);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesStore preferencesStore = new PreferencesStore(context);
                if (TextUtils.isEmpty(preferencesStore.getAuthToken())) {
                    Intent i = new Intent(context, FrontPageActivity.class);
                    context.startActivity(i);
                } else {
                    if (((ToggleButton) v).isChecked()) {
                        handleWish();
                    } else {
                        handleUnwish();
                    }
                }
            }
        });
        mContext = context;
    }

    public void setVenue(Venue venue) {
        mVenue = venue;
        setChecked(mVenue.isWishlisted());
    }

    private void handleWish() {
        mHandler.removeMessages(UNWISH);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(WISH, mVenue), 300);

    }

    private void handleUnwish() {
        mHandler.removeMessages(WISH);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(UNWISH, mVenue), 300);
    }

    private void wish(final Venue venue) {
        // venue is already wished
        if (venue.isWishlisted()) return;

        venue.setWishlisted(true);
        VenueSynchronizer.getInstance().wish(venue.id);

        BurppleApi api = BurppleApi.getInstance(getContext());
        api.enqueue(WishRequest.wish(venue.id, new EmptyListener() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                venue.setWishlisted(false);
                VenueSynchronizer.getInstance().unwish(venue.id);
                if (mVenue.id == venue.id) setChecked(false);
            }
        }));
    }

    private void unwish(final Venue venue) {
        // venue is already unwished
        if (!venue.isWishlisted()) return;

        venue.setWishlisted(false);
        VenueSynchronizer.getInstance().unwish(venue.id);

        BurppleApi api = BurppleApi.getInstance(getContext());
        api.enqueue(WishRequest.unwish(venue.id, new EmptyListener() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                venue.setWishlisted(true);
                VenueSynchronizer.getInstance().wish(venue.id);
                if (mVenue.id == venue.id) setChecked(true);
            }
        }));
    }

    private static final int WISH = 0xf0;
    private static final int UNWISH = 0x0f;
    private static class WishEventHandler extends Handler {
        private final WeakReference<WishButton> mWishRef;

        public WishEventHandler(WishButton wishButton) {
            mWishRef = new WeakReference<WishButton>(wishButton);
        }

        @Override
        public void handleMessage(Message msg) {
            WishButton w = mWishRef.get();
            if (msg.what == WISH) {
                w.wish((Venue) msg.obj);
            } else if (msg.what == UNWISH) {
                w.unwish((Venue) msg.obj);
            }
        }

    }

}
