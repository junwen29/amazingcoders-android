package com.amazingcoders_android.sync;

import com.amazingcoders_android.models.Deal;

/**
 * Created by junwen29 on 9/26/2015.
 */
public class DealSynchronizer extends Synchronizer<Deal>{
    private static DealSynchronizer sInstance;

    public static DealSynchronizer getInstance() {
        if (sInstance == null) {
            sInstance = new DealSynchronizer();
        }

        return sInstance;
    }

    public void bookmark(long id) {
        if (!has(id)) return;

        for (Deal d : getObjects()) {
            if (d.id == id) {
                d.setBookmarked(true);
            }
        }
    }

    public void unbookmark(long id) {
        if (!has(id)) return;

        for (Deal v : getObjects()) {
            if (v.id == id) {
                v.setBookmarked(false);
            }
        }
    }
}
