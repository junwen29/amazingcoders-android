package com.amazingcoders_android.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;

import com.amazingcoders_android.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by junwen29 on 9/24/2015.
 */
public class AmazingHelper {
    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    public static final long WEEK_MILLIS = 7 * DAY_MILLIS;
    public static final long YEAR_MILLIS = 52 * WEEK_MILLIS;


    public static int convertDipToPx(int dp, Resources res) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static int convertSpToPx(int sp, Resources res){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp , res.getDisplayMetrics());
    }

    public static int getSecondsOffsetFromUtc() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getOffset(System.currentTimeMillis()) / 1000;
    }

    public static String printDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        if (date == null) date = new Date();
        return formatter.format(date);
    }

    public static String convertDateFormat(String date, String inFormat, String outFormat) {
        if (TextUtils.isEmpty(date)) return "";

        SimpleDateFormat inFormatter = new SimpleDateFormat(inFormat, Locale.US);
        SimpleDateFormat outFormatter = new SimpleDateFormat(outFormat, Locale.US);

        try {
            return outFormatter.format(inFormatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date parseDate(String date, String format) {
        if (TextUtils.isEmpty(date)) return new Date();

        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }
}
