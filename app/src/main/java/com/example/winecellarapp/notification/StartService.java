package com.example.winecellarapp.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Class starting Service for code version less than LOLLIPOP
 */
public class StartService
{
    private static Intent serviceIntent = null;

    /**
     * set Context of the Application to Intent
     * @param context contains Context of the application
     */
    private void setIntent(Context context)
    {
        if (serviceIntent == null)
            serviceIntent = new Intent(context, NotificationService.class);
    }
    /**
     * Check version of the android and lunch valid service for it
     */
    public void startService(Context context) {
        if (context == null)
            return;
        setIntent(context);

        //check the android version and run valid service(newest android version are not supporting startService)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(serviceIntent);
        else
            context.startService(serviceIntent);
        Log.d("NotificationService", "CreateNotification NotificationService started");
    }

    //TODO:UNTESTED METHOD
    /**
     * Stop service
     * @param context
     */
    public void stopService(Context context)
    {

        if (context == null)
            return;
        setIntent(context);
        context.stopService(serviceIntent);
        Log.d("NotificationService", "CreateNotification Service ended");
    }
}

