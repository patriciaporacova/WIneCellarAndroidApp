package com.example.winecellarapp.presenters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Threshold;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Patricia Poracova
 **/
public class SettingsPresenter {

    /**
     * Method for sending new Sensor Threshold
     * It is triggered by change in shared preference
     *
     * @param sensor contains name of sensor which thresholds are being changes
     * @param low contains value of lowest threshold
     * @param high contains value of highest threshold
     */
    public void setSensorThresholds(String sensor, double low, double high) {
        Call<Void> newTreshold = Utils.getApi().setThresholds(sensor, low, high);
        newTreshold.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("newValues", "New low '" + low + "' new high" + high);

                        /*if (response.isSuccessful() && response.body() != null) {
                            view.setData(response.body());

                        } else {
                            view.onErrorLoading(response.message());
                        }*/
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    /**
     * Method for sending new temperature Threshold
     * It is triggered by change of either low or high threshold in shared preference
     *
     * @param threshold contains threshold object being updated
     */
    public void setTempThreshold(Threshold threshold) {
        Call<Threshold> setNewTempThreshold = Utils.getApi().setTempThreshold(threshold);
        setNewTempThreshold.enqueue(new Callback<Threshold>() {
            @Override
            public void onResponse(Call<Threshold> call, Response<Threshold> response) {

            }

            @Override
            public void onFailure(Call<Threshold> call, Throwable t) {

            }
        });
    }
}
