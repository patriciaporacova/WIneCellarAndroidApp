package com.example.winecellarapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.model.Threshold;
import com.example.winecellarapp.presenters.SettingsPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Patricia Poracova
 * class responsible for setting thresholds and notification preferences*/
public class SettingsActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SettingsPresenter presenter;
    Threshold threshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        /**get current preferences*/
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new SettingsPresenter();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    /**setListener for changes to sharedPreferences, every change triggers new API call to post new threshold levels*/
    SharedPreferences.OnSharedPreferenceChangeListener myPrefListner = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            Log.d("changePref", "The key '" + key + "' was changed");
            switch (key) {
                case ("lowest_temp"):
                case ("highest_temp"):

                    double lowT = Double.parseDouble(prefs.getString("lowest_temp", null));
                    double highT = Double.parseDouble(prefs.getString("highest_temp", null));
                    threshold= new Threshold("temperature", lowT, highT);
                    presenter.setTempThreshold(threshold);
                    break;
                case ("lowest_humidity"):
                case ("highest_humidity"):

                    double lowH = Double.parseDouble(prefs.getString("lowest_humidity", null));
                    double highH = Double.parseDouble(prefs.getString("highest_humidity", null));
                    presenter.setSensorThresholds("humidity", lowH, highH);
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
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}