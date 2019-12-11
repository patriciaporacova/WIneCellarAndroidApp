package com.example.winecellarapp.REST;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class Utils {
    public static CellarAPI getApi() {
        return CellarClient.getCellarClient().create(CellarAPI.class);
    }

    public static AlertDialog showDialogMessage(View context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context.getContext()).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }
}
