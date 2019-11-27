package com.example.winecellarapp;

import androidx.annotation.NonNull;

import com.example.winecellarapp.REST.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {

    private DataView view;

    public Presenter(DataView view) {
        this.view = view;
    }

    public void getSensorData() {
        Call<Model> mealsCall = Utils.getApi().getData();
        mealsCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {


                if (response.isSuccessful() && response.body() != null) {

                    view.setData(response.body().getAllData());

                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }

        });
    }

}
