package com.example.winecellarapp.presenters;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Threshold;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Patricia Poracova
 * Edited by Jakub Piga
 **/
public class SettingsPresenter {

    private boolean sent;
    /**
     * Method for sending new Threshold to specific sensor
     * It is triggered by change of either low or high threshold in shared preference
     *
     * @param threshold contains threshold object being updated
     * return: boolean if sensor thresholds were changed
     */
    public boolean setSensorThreshold(Threshold threshold,String sensorType)
    {
        sent = false;
        Call<Threshold> setNewSensorThreshold;
        switch (sensorType)
        {
            case ("temperature"):
            {
                setNewSensorThreshold = Utils.getApi().setTempThreshold(threshold);
                break;
            }
            case ("Co2"):
            {
                setNewSensorThreshold = Utils.getApi().setAirThreshold(threshold);
                break;
            }
            case ("humidity"):
            {
                setNewSensorThreshold = Utils.getApi().setHumidityThreshold(threshold);
                break;
            }
            default:
            {
                setNewSensorThreshold = null;
                break;
            }

        }
        if(setNewSensorThreshold != null)
        {
            setNewSensorThreshold.enqueue(new Callback<Threshold>() {
                @Override
                public void onResponse(Call<Threshold> call, Response<Threshold> response)
                {
                    sent = true;
                }

                @Override
                public void onFailure(Call<Threshold> call, Throwable t)
                {
                    sent = false;
                }
            });
            sent = true;
        }
        else
        {
            sent = false;
        }
        return  sent;
    }


}
