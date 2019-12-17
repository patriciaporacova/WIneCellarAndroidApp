package com.example.winecellarapp.notification;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.winecellarapp.R;
import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.notification.notificationDesign.CreateNotification;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {
    public static final String RESTART_INTENT = "com.example.notification.restarter";
    private static String TAG = "NotificationService";

    //static to avoid multiple timers
    private static Timer timer;
    private static TimerTask timerTask;
    private double tempMin;
    private double tempMax;
    CreateNotification temperatureNotification;
    public NotificationService()
    {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            restartForeground();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "restarting NotificationService !!");

        // it has been killed by Android and now it is restarted. We must make sure to have reinitialised everything
        if (intent == null) {
            StartService bck = new StartService();
            bck.startService(this);
        }
        startAPIObserver();

        // return start sticky so if it is killed by android, it will be restarted with Intent null
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * it starts the process in foreground. Normally this is done when screen goes off
     * THIS IS REQUIRED IN ANDROID 8 :
     * "The system allows apps to call Context.startForegroundService()
     * even while the app is in the background.
     * However, the app must call that service's startForeground() method within five seconds
     * after the service is created."
     */
    public void restartForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "restarting foreground");
            try
            {
                CreateNotification createNotification = new CreateNotification();
                startForeground(1234, createNotification.setNotification(this,
                        "WineCellar service", "Controlling current temperature",
                        R.drawable.ic_bell,false,R.color.ColorCardBgr));
                Log.i(TAG, "restarting foreground successful");
                startAPIObserver();
            }
            catch (Exception e) {
                Log.e(TAG, "Error in notification " + e.getMessage());
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called");
        // restart the never ending service
        Intent broadcastIntent = new Intent(RESTART_INTENT);
        sendBroadcast(broadcastIntent);
    }


    /**
     * Called when the process is killed by Android
     *
     * @param rootIntent includes main android Intent
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemoved called");

        // restart the never ending service
        Intent broadcastIntent = new Intent(RESTART_INTENT);
        sendBroadcast(broadcastIntent);
    }

    public void startAPIObserver() {
        timer = new Timer();
        getMaxAndMinValues();
        initializeAPIObserverTask();
        Log.i(TAG, "Timer schedule");
        timer.schedule(timerTask, 10000, 10000); //
    }

    /**
     * Gets temperature from database when schedule fires it
     */
    public void initializeAPIObserverTask() {
        Log.i(TAG, "initialising TimerTask");
        timerTask = new TimerTask() {
            public void run() {
                checkCurrentTemperature();
            }
        };
    }

    /**
     * Get current temperature from database through the API
     */
    private void checkCurrentTemperature() {

        Call<Temperature> temperature = Utils.getApi().getLastTemperature("temperature");
        temperature.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response) {

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getReading() > tempMax)
                    {
                        createTempWarningNotification("Temperature too HIGH",R.color.colorRed);
                    }
                    else if(response.body().getReading() < tempMin)
                    {
                        createTempWarningNotification("Temperature too LOW",R.color.lowTemperature);
                    }
                        Log.i(TAG, (response.body()).getReading().toString());

                } else {
                    createTempWarningNotification("Problem with controlling temperature",R.color.colorAccent);
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                createTempWarningNotification("Problem with controlling temperature",R.color.colorAccent);
            }

        });

    }

    private void getMaxAndMinValues() {

        Call<List<Temperature>> temperature = Utils.getApi().getThresholds();
        temperature.enqueue(new Callback<List<Temperature>>() {
            @Override
            public void onResponse(@NonNull Call<List<Temperature>> call, @NonNull Response<List<Temperature>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    tempMin = response.body().get(0).getReading();
                    tempMax = response.body().get(1).getReading();

                } else
                    Log.i(TAG, response.message());
            }

            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }

        });

    }

    private void createTempWarningNotification(String message, int color)
    {
        if(temperatureNotification ==  null)
            temperatureNotification = new CreateNotification();

        startForeground(1234, temperatureNotification.setNotification(this,"Temperature WARNING", message,R.drawable.ic_bell,true, color));
    }

}
