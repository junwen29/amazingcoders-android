package com.amazingcoders_android.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by junwen29 on 9/19/2015.
 */
public class ProgressDialogFactory {

    public static ProgressDialog create(Context context, int message) {
        return create(context, message, false);
    }

    public static ProgressDialog create(Context context, int message, boolean cancelable) {
        return create(context, message, cancelable, true);
    }

    public static ProgressDialog create(Context context, int message, boolean cancelable, boolean indeterminate) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(message));
        dialog.setCancelable(cancelable);
        dialog.setIndeterminate(indeterminate);
        return dialog;
    }
}
