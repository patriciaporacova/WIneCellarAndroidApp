package com.example.winecellarapp.presenters;

import androidx.annotation.NonNull;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.REST.DatePathFormatter;
import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Humidity;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HumidityPresenter
{
    private DataView view;

    public HumidityPresenter(DataView view)
    {
        this.view = view;
    }

    public void getHumiditySensorData()
    {
        Call<Humidity> humidity = Utils.getApi().getLastHumidity("Humidity");
        humidity.enqueue(new Callback<Humidity>()
        {
            @Override
            public void onResponse(Call<Humidity> call, Response<Humidity> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    view.setData(response.body());
                } else
                {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Humidity> call, Throwable t)
            {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    public void getHumidityBetweenData(Date start, Date end)
    {
        DatePathFormatter startFormated = new DatePathFormatter(start);
        DatePathFormatter endFormated = new DatePathFormatter(end);
        Call<List<Humidity>> humidity = Utils.getApi().getAvarageHumidityBetween("Humidity", startFormated, endFormated);
        humidity.enqueue(new Callback<List<Humidity>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<Humidity>> call, @NonNull Response<List<Humidity>> response)
            {

                if (response.isSuccessful() && response.body() != null)
                {
                    view.setListData(response.body());

                } else
                {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Humidity>> call, Throwable t)
            {
                view.onErrorLoading(t.getLocalizedMessage());

            }
        });

        //TODO-ERIC: Same logic like temperature presenter
    }
}

