package com.example.winecellarapp.notification;


import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.winecellarapp.R;
import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Threshold;
import com.example.winecellarapp.notification.notificationDesign.CreateNotification;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jakub Piga
 * Notification service which runs in the background even doe user destroy the app
 */
public class NotificationService extends Service {
    public static final String RESTART_INTENT = "com.example.notification.restarter";
    private static String TAG = "NotificationService";

    //static to avoid multiple timers
    private static Timer timer;
    private static TimerTask timerTask;
    private double tempMin;
    private double humMin;
    private double airMin;
    CreateNotification temperatureCreateNotification;

    public NotificationService()
    {
        super();
    }


    /**
     * On create for service
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            restartForeground();
        }
    }

    /**
     * On start command for service, called when it was killed by android and it is restarted
     * @param intent of the application
     * @param flags for running service
     * @param startId of the service
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //Make sure to reinitialised everything
        if (intent == null) {
            StartService bck = new StartService();
            bck.startService(this);
        }

        startAPIObserver();

        // return start sticky so if it is killed by android, it will be restarted with Intent null
        return START_STICKY;
    }


    /**
     * Binder of the service
     * @param intent of the application
     * @return IBinder in our case NULL
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Starts the process in foreground.(When screen goes off)
     * REQUIRED IN ANDROID 8:
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
                        "WineCellar service", "Controlling cellar conditions",
                        R.drawable.ic_bell,false,R.color.ColorCardBgr));
                Log.i(TAG, "restarting foreground successful");
                startAPIObserver();
            }
            catch (Exception e) {
                Log.e(TAG, "Error in notification " + e.getMessage());
            }
        }
    }


    /**
     * When application is closed onDestroy is called and it will restart service
     * because android will kill every service with itself
     */
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
     * @param rootIntent includes android Intent
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemoved called");

        // restart the never ending service
        Intent broadcastIntent = new Intent(RESTART_INTENT);
        sendBroadcast(broadcastIntent);
    }

    /**
     * Create Timer for observing database with delay of 10000(every 10 sec)
     *
     */
    public void startAPIObserver() {
        timer = new Timer();
        getMaxAndMinValues();
        initializeAPIObserverTask();
        timer.schedule(timerTask, 10000, 10000); //
    }

    /**
     * Gets temperature from database when schedule fires it
     */
    public void initializeAPIObserverTask() {
        timerTask = new TimerTask() {
            public void run() {
                checkOutOfBoundsValues();
            }
        };
    }

    /**
     * Get current temperature from database through the API
     */
    private void checkOutOfBoundsValues() {

        Call<List<Threshold>> thresholds = Utils.getApi().getOutOfBound();
        thresholds.enqueue(new Callback<List<Threshold>>() {
            @Override
            public void onResponse(@NonNull Call<List<Threshold>> call, @NonNull Response<List<Threshold>> response) {

                if (response.isSuccessful() && response.body() != null)
                {
                    if(response.body().size() != 0)
                        checkOutOfBoundSensor(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Threshold>> call, Throwable t)
            {
                Log.i(TAG, t.getLocalizedMessage());
            }

        });

    }

    /**
     * Get maximum and minimum values from the database
     * We are using just min values because database send always request if there
     * is some "out of bound" value, so if minValue is not "out of bound" max value has to be
     */
    private void getMaxAndMinValues() {

        Call<List<Threshold>> temperature = Utils.getApi().getThresholds();
        temperature.enqueue(new Callback<List<Threshold>>() {
            @Override
            public void onResponse(@NonNull Call<List<Threshold>> call, @NonNull Response<List<Threshold>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    tempMin = response.body().get(0).getMinValue();
                    humMin = response.body().get(1).getMinValue();
                    airMin = response.body().get(2).getMinValue();

                }
                else
                    Log.i(TAG, response.message());
            }

            @Override
            public void onFailure(Call<List<Threshold>> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }

        });

    }

    /**
     * Initializes CreateNotification if null
     * Calls creates warning notification
     * @param message
     * @param color
     */
    private void createTempWarningNotification(String message, int color)
    {
        if(temperatureCreateNotification ==  null)
            temperatureCreateNotification = new CreateNotification();

        startForeground(1234, temperatureCreateNotification.setNotification(this,"Temperature WARNING", message,R.drawable.ic_bell,true, color));
    }

    /**
     * Check which sensor is out of bound and create notification depends on it
     * @param thresholds includes values which are out of bounds
     */
    private void checkOutOfBoundSensor(List<Threshold> thresholds)
    {
        for (int i = 0; i < thresholds.size(); i++)
        {
            if(thresholds.get(i) != null)
            {
                if(thresholds.get(i).getSensorType().equalsIgnoreCase("CO2"))
                {
                    if (thresholds.get(i).getMinValue() < airMin)
                        createTempWarningNotification("CO2 too LOW",R.color.lowTemperature);
                    else
                        createTempWarningNotification("CO2 too HIGH",R.color.colorRed);
                }
                else if(thresholds.get(i).getSensorType().equalsIgnoreCase("Temperature"))
                {
                    if (thresholds.get(i).getMinValue() < tempMin)
                        createTempWarningNotification("Temperature too LOW",R.color.lowTemperature);
                    else
                        createTempWarningNotification("CO2 too HIGH",R.color.colorRed);
                }
                else if(thresholds.get(i).getSensorType().equalsIgnoreCase("Humidity"))
                {
                    if (thresholds.get(i).getMinValue() < humMin)
                        createTempWarningNotification("Humidity too LOW",R.color.lowTemperature);
                    else
                        createTempWarningNotification("Humidity too HIGH",R.color.colorRed);
                }
                else
                {

                }
            }
        }
    }

}
