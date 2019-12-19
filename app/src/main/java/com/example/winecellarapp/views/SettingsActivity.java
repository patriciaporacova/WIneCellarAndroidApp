package com.example.winecellarapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.winecellarapp.GetSetSharedPreferences;
import com.example.winecellarapp.R;
import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.model.Threshold;
import com.example.winecellarapp.notification.GlobalNotificationSharedPref;
import com.example.winecellarapp.notification.NotificationService;
import com.example.winecellarapp.notification.StartService;
import com.example.winecellarapp.notification.restarter.RestartServiceBroadcastReceiver;
import com.example.winecellarapp.presenters.SettingsPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Patricia Poracova
 * class responsible for setting thresholds and notification preferences
 * Edited by Jakub Piga
 * */
public class SettingsActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SettingsPresenter presenter;
    Threshold threshold;
    GetSetSharedPreferences getSetSharedPreferences;
    GlobalNotificationSharedPref notificationSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        /**get current preferences*/
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new SettingsPresenter();
        getSetSharedPreferences = new GetSetSharedPreferences(getApplicationContext());
        notificationSharedPref = new GlobalNotificationSharedPref(getApplicationContext());
        createTempSharedPreferences();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();


    }

    /**setListener for changes to sharedPreferences, every change triggers new API call to post new threshold levels*/
    SharedPreferences.OnSharedPreferenceChangeListener myPrefListner = new SharedPreferences.OnSharedPreferenceChangeListener()
    {

        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            Log.d("changePref", "The key '" + key + "' was changed");
            switch (key)
            {
                case ("lowest_temp"):
                case ("highest_temp"):
                {
                    double lowT = Double.parseDouble(prefs.getString("lowest_temp", ""));
                    double highT = Double.parseDouble(prefs.getString("highest_temp", ""));
                    threshold= new Threshold("temperature", lowT, highT);
                    if(!(presenter.setSensorThreshold(threshold,"temperature")))
                        setBackSharedPreferences("temperature");
                    break;
                }
                case ("lowest_humidity"):
                case ("highest_humidity"):
                {
                    double lowT = Double.parseDouble(prefs.getString("lowest_humidity", ""));
                    double highT = Double.parseDouble(prefs.getString("highest_humidity", ""));
                    threshold= new Threshold("Humidity", lowT, highT);
                    //if it was not change in database leave previous values
                    if(!(presenter.setSensorThreshold(threshold,"humidity")))
                        setBackSharedPreferences("humidity");
                    break;
                }
                case ("lowest_air"):
                case ("highest_air"):
                {
                    double lowT = Double.parseDouble(prefs.getString("lowest_air", ""));
                    double highT = Double.parseDouble(prefs.getString("highest_air", ""));
                    threshold= new Threshold("CO2", lowT, highT);
                    if(!(presenter.setSensorThreshold(threshold,"Co2")))
                        setBackSharedPreferences("air");
                    break;
                }
                case "air":
                {
                    if(getSetSharedPreferences.getBooleanSharedPreferences("air_notify") == true)
                        notificationSharedPref.setShowNotificationForPreference("air_notify","no");
                    else
                        notificationSharedPref.setShowNotificationForPreference("air_notify","yes");

                    checkStopOrStartService();
                    break;
                }

                case "humidity":
                {
                    if(getSetSharedPreferences.getBooleanSharedPreferences("humidity_notify") == true)
                        notificationSharedPref.setShowNotificationForPreference("humidity_notify","yes");
                    else
                        notificationSharedPref.setShowNotificationForPreference("humidity_notify","no");

                    checkStopOrStartService();
                    break;
                }

                case "temperature":
                {
                    if(getSetSharedPreferences.getBooleanSharedPreferences("temperature_notify") == true)
                        notificationSharedPref.setShowNotificationForPreference("temperature_notify","yes");
                    else
                        notificationSharedPref.setShowNotificationForPreference("temperature","no");

                    checkStopOrStartService();
                }

                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(myPrefListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.unregisterOnSharedPreferenceChangeListener(myPrefListner);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
        {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            androidx.preference.EditTextPreference editTextPreference = getPreferenceManager().findPreference("lowest_temp");
            editTextPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));
            androidx.preference.EditTextPreference editText2Preference = getPreferenceManager().findPreference("highest_temp");
            editText2Preference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));
            androidx.preference.EditTextPreference editText3Preference = getPreferenceManager().findPreference("highest_air");
            editText3Preference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));
            androidx.preference.EditTextPreference editText4Preference = getPreferenceManager().findPreference("lowest_humidity");
            editText4Preference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));
            androidx.preference.EditTextPreference editText5Preference = getPreferenceManager().findPreference("highest_humidity");
            editText5Preference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));
            androidx.preference.EditTextPreference editText6Preference = getPreferenceManager().findPreference("lowest_air");
            editText6Preference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void createTempSharedPreferences()
    {
        getSetSharedPreferences.editSharedPreferences("lowest_temp_temp"
                ,getSetSharedPreferences.getSharedPreferences("lowest_temp"));
        getSetSharedPreferences.editSharedPreferences("highest_temp_temp"
                ,getSetSharedPreferences.getSharedPreferences("highest_temp"));
        getSetSharedPreferences.editSharedPreferences("lowest_humidity_temp"
                ,getSetSharedPreferences.getSharedPreferences("lowest_humidity"));
        getSetSharedPreferences.editSharedPreferences("highest_humidity_temp"
                ,getSetSharedPreferences.getSharedPreferences("highest_humidity"));
        getSetSharedPreferences.editSharedPreferences("lowest_air_temp"
                ,getSetSharedPreferences.getSharedPreferences("lowest_air"));
        getSetSharedPreferences.editSharedPreferences("highest_air_temp"
                ,getSetSharedPreferences.getSharedPreferences("highest_air"));

    }

    private void setBackSharedPreferences(String nameOfTheReference)
    {

        getSetSharedPreferences.editSharedPreferences("lowest_"+nameOfTheReference
                ,getSetSharedPreferences.getSharedPreferences("lowest_"+nameOfTheReference+"_temp"));
        getSetSharedPreferences.editSharedPreferences("highest_"+nameOfTheReference
                ,getSetSharedPreferences.getSharedPreferences("highest_"+nameOfTheReference+"_temp"));
    }

    private void checkStopOrStartService()
    {
        if(getSetSharedPreferences.getBooleanSharedPreferences("air") == false &&
                getSetSharedPreferences.getBooleanSharedPreferences("humidity") == false &&
                getSetSharedPreferences.getBooleanSharedPreferences("temperature") == false)
        {
            notificationSharedPref.editServiceSharedPreferences("off");
            NotificationService.notificationService.stopForeground(true);
        }
        else
        {

                notificationSharedPref.editServiceSharedPreferences("on");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    RestartServiceBroadcastReceiver.scheduleJob(getApplicationContext());
                } else
                    {
                    StartService bck = new StartService();
                    bck.startService(getApplicationContext());
                }
        }
    }
}