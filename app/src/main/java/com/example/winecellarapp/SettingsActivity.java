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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    //SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

    }

    SharedPreferences.OnSharedPreferenceChangeListener myPrefListner = new SharedPreferences.OnSharedPreferenceChangeListener(){
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            Log.d("changePref", "The key '" + key + "' was changed");
            switch (key){
            case "lowest_temp":

               double lowD=Double.parseDouble(prefs.getString("lowest_temp", null ));
               double highD=Double.parseDouble(prefs.getString("highest_temp", null));

                Threshold threshold= new Threshold("temperature", lowD, highD);

                Log.d("newValues", "New low '" + lowD + "' new high" +highD);
                



                /*Call<Void> humidityTreshold = Utils.getApi().setHumidityThresholds("temperature",lowD, highD);
                humidityTreshold.enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response)
                    {
                        Log.d("newValues", "New low '" + lowD + "' new high" +highD);

                        *//*if (response.isSuccessful() && response.body() != null) {
                            view.setData(response.body());

                        } else {
                            view.onErrorLoading(response.message());
                        }*//*
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        //view.onErrorLoading(t.getLocalizedMessage());
                    }

                });*/
            }
        }
    };

    // PreferenceManager.getDefaultSharedPreferences(this)

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