package com.amazingcoders_android.fragments;

import com.amazingcoders_android.api.CollectionListener;
import com.amazingcoders_android.api.requests.DealRequest;
import com.amazingcoders_android.fragments.base.DealFragment;
import com.amazingcoders_android.models.Deal;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by junwen29 on 9/26/2015.
 */
public class BookmarkDealsFragment extends DealFragment {

    public static final String TYPE = "bookmark";

    public BookmarkDealsFragment() {
    }

    @Override
    public void loadDeals() {
        CollectionListener<Deal> listener = new CollectionListener<Deal>() {
            @Override
            public void onResponse(Collection<Deal> deals) {
                mSwipeLayout.setRefreshing(false);
                mAdapter.clear();
                mAdapter.addAll(deals);
                mAdapter.notifyDataSetChanged();
                mOriginalDeals = new ArrayList<>(deals);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mSwipeLayout.setRefreshing(false);
                showErrorMessage();
            }
        };
        mSwipeLayout.setRefreshing(true);
        getBurppleApi().enqueue(DealRequest.activeDealsByType(TYPE, listener));
    }
}