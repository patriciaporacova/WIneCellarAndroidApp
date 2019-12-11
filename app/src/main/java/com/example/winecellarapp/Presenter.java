package com.example.winecellarapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.winecellarapp.Model.Temperature;
import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.fragments.TemperatureFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {

    private DataView view;
    private TemperatureFragment fragment;

    public Presenter(DataView view) {
        this.view = view;
    }

    public void getSensorData() {
        Call<Temperature> mealsCall = Utils.getApi().getLastTemperature();
        Log.v("skusam tu", "v callback");
        mealsCall.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response) {


                if (response.isSuccessful() && response.body() != null) {
                    view.setData(response.body());
                   Log.v("som tu", "v metode");

                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }

        });
    }

}
