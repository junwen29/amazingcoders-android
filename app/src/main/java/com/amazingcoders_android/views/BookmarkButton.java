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
import com.amazingcoders_android.api.requests.BookmarkRequest;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.helpers.PreferencesStore;
import com.amazingcoders_android.models.Deal;
import com.amazingcoders_android.sync.DealSynchronizer;
import com.android.volley.VolleyError;

import java.lang.ref.WeakReference;

/**
 * Created by junwen29 on 9/26/2015.
 */
public class BookmarkButton extends ToggleButton{

    static final String TAG = "BookmarkButton";

    // checked = bookmarked -> unbookmark
    // !checked = !bookmarked -> bookmark

    private Deal mDeal;
    private Handler mHandler;
    private Context mContext;

    public BookmarkButton(Context context) {
        super(context);
        init(context);
    }

    public BookmarkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BookmarkButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        mHandler = new BookmarkEventHandler(this);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesStore preferencesStore = new PreferencesStore(context);
                if (TextUtils.isEmpty(preferencesStore.getAuthToken())) {
                    Intent i = new Intent(context, FrontPageActivity.class);
                    context.startActivity(i);
                } else {
                    if (((ToggleButton) v).isChecked()) {
                        handleBookmark();
                    } else {
                        handleUnbookmark();
                    }
                }
            }
        });
        mContext = context;
    }

    public void setDeal(Deal deal) {
        mDeal = deal;
        setChecked(mDeal.isBookmarked());
    }

    private void handleBookmark() {
        mHandler.removeMessages(UNBOOKMARK);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(BOOKMARK, mDeal), 300);

    }

    private void handleUnbookmark() {
        mHandler.removeMessages(BOOKMARK);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(UNBOOKMARK, mDeal), 300);
    }

    private void bookmark(final Deal deal) {
        // deal is already bookmarked
        if (deal.isBookmarked()) return;

        deal.setBookmarked(true);
        DealSynchronizer.getInstance().bookmark(deal.id);

        BurppleApi api = BurppleApi.getInstance(getContext());
        api.enqueue(BookmarkRequest.bookmark(deal.id, new EmptyListener() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                deal.setBookmarked(false);
                DealSynchronizer.getInstance().unbookmark(deal.id);
                if (mDeal.id == deal.id) setChecked(false);
            }
        }));
    }

    private void unbookmark(final Deal deal) {
        // deal is already unbookmarked
        if (!deal.isBookmarked()) return;

        deal.setBookmarked(false);
        DealSynchronizer.getInstance().unbookmark(deal.id);

        BurppleApi api = BurppleApi.getInstance(getContext());
        api.enqueue(BookmarkRequest.unbookmark(deal.id, new EmptyListener() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                deal.setBookmarked(true);
                DealSynchronizer.getInstance().bookmark(deal.id);
                if (mDeal.id == deal.id) setChecked(true);
            }
        }));
    }

    private static final int BOOKMARK = 0xf0;
    private static final int UNBOOKMARK = 0x0f;
    private static class BookmarkEventHandler extends Handler {
        private final WeakReference<BookmarkButton> mBookmarkRef;

        public BookmarkEventHandler(BookmarkButton bookmarkButton) {
            mBookmarkRef = new WeakReference<BookmarkButton>(bookmarkButton);
        }

        @Override
        public void handleMessage(Message msg) {
            BookmarkButton b = mBookmarkRef.get();
            if (msg.what == BOOKMARK) {
                b.bookmark((Deal) msg.obj);
            } else if (msg.what == UNBOOKMARK) {
                b.unbookmark((Deal) msg.obj);
            }
        }

    }
}
