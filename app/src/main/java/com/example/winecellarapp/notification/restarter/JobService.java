package com.example.winecellarapp.notification.restarter;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.winecellarapp.notification.StartService;


/**
 * This code has been developed by Fabio Ciravegna, The University of Sheffield.
 * https://github.com/fabcira/neverEndingProcessAndroid7-/blob/master/app/src/main/java/uk/ac/shef/oak/jobserviceexample/restarter/JobService.java
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobService extends android.app.job.JobService {
    private static String TAG= JobService.class.getSimpleName();
    private static RestartServiceBroadcastReceiver restartSensorServiceReceiver;
    public static final String RESTART_INTENT = "com.example.notification.restarter";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        StartService bck = new StartService();
        bck.startService(this);
        registerRestarterReceiver();
        return false;
    }

    private void registerRestarterReceiver() {

        // the context can be null if app just installed and this is called from restartsensorservice
        // https://stackoverflow.com/questions/24934260/intentreceiver-components-are-not-allowed-to-register-to-receive-intents-when
        // Final decision: in case it is called from installation of new version (i.e. from manifest, the application is
        // null. So we must use context.registerReceiver. Otherwise this will crash and we try with context.getApplicationContext
        if (restartSensorServiceReceiver == null)
            restartSensorServiceReceiver = new RestartServiceBroadcastReceiver();
        else try{
            unregisterReceiver(restartSensorServiceReceiver);
        } catch (Exception e){
            // not registered
        }
        // give the time to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // we register the  receiver that will restart the background service if it is killed
                // see onDestroy of NotificationService
                IntentFilter filter = new IntentFilter();
                filter.addAction(RESTART_INTENT);
                try {
                   registerReceiver(restartSensorServiceReceiver, filter);
                } catch (Exception e) {
                    try {
                        getApplicationContext().registerReceiver(restartSensorServiceReceiver, filter);
                    } catch (Exception ex) {

                    }
                }
            }
        }, 10000);

    }

    /**
     * called if Android kills the job service
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "Stopping job");
        Intent broadcastIntent = new Intent(RESTART_INTENT);
        sendBroadcast(broadcastIntent);
        // give the time to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unregisterReceiver(restartSensorServiceReceiver);
            }
        }, 10000);

        return false;
    }
}