package com.example.winecellarapp.REST;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by patricia Poracova
 * Create new API client
 */
public class Utils {
    public static CellarAPI getApi() {
        return CellarClient.getCellarClient().create(CellarAPI.class);
    }

    /**
     * Create alert dialog if something went wrong
     * @param context root context
     * @param title title of the alert dialog
     * @param message message of the alert dialog
     * @return new alert dialog
     */
    public static AlertDialog showDialogMessage(View context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context.getContext()).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }
}
