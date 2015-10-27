package com.amazingcoders_android.helpers;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by junwen29 on 10/25/2015.
 */
public class Helper {

    public static int convertDipToPx(int dp, Resources res) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }
}
