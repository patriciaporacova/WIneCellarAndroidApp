package com.example.winecellarapp;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.winecellarapp.Model.Hello;
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

       /* Call<Hello> mealsCall = Utils.getApi().getHello();
        Log.d("som tu",  mealsCall.toString());
        mealsCall.enqueue(new Callback<Hello>() {
            @Override
            public void onResponse(@NonNull Call<Hello> call, @NonNull Response<Hello> response) {

                Log.d("som dnu",  response.body().toString());
                if (response.isSuccessful() && response.body() != null) {
                    view.setHello(response.body());



                } else {
                    view.onErrorLoading(response.message());

                }
            }

            @Override
            public void onFailure(Call<Hello> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }

        });*/


       Call<Temperature> mealsCall = Utils.getApi().getLastTemperature();
        Log.d("som tu",  mealsCall.toString());
        mealsCall.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response) {

            Log.d("som dnu",  response.body().toString());
                if (response.isSuccessful() && response.body() != null) {
                    view.setData(response.body());



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
