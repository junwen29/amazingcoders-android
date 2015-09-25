package com.amazingcoders_android.sync;

import com.amazingcoders_android.models.Venue;

/**
 * Created by junwen29 on 9/25/2015.
 */
public class VenueSynchronizer extends Synchronizer<Venue>{
    private static VenueSynchronizer sInstance;

    public static VenueSynchronizer getInstance() {
        if (sInstance == null) {
            sInstance = new VenueSynchronizer();
        }

        return sInstance;
    }

    public void wish(long id) {
        if (!has(id)) return;

        for (Venue v : getObjects()) {
            if (v.id == id) {
                v.setWishlisted(true);
            }
        }
    }

    public void unwish(long id) {
        if (!has(id)) return;

        for (Venue v : getObjects()) {
            if (v.id == id) {
                v.setWishlisted(false);
            }
        }
    }
}
