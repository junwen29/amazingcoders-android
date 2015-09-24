package com.amazingcoders_android.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.amazingcoders_android.R;

/**
 * Created by junwen29 on 9/19/2015.
 */
public class AlertDialogFactory {

    public static AlertDialog networkError(Context context) {
        return new AlertDialog.Builder(context).setTitle(R.string.dialog_no_network)
                .setMessage(R.string.dialog_check_network)
                .setNegativeButton(R.string.no, null)
                .create();
    }

    public static AlertDialog logout(Context context, DialogInterface.OnClickListener okListener) {
        return new AlertDialog.Builder(context).setTitle(R.string.dialog_logout)
                .setMessage(R.string.dialog_confirmation)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
