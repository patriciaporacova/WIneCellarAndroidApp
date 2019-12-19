package com.example.winecellarapp.presenters;

import androidx.annotation.NonNull;

import com.example.winecellarapp.views.DataView;
import com.example.winecellarapp.REST.DatePathFormatter;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.REST.Utils;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jakub Piga
 * Class sending API calls and sending response to view trough the DataView interface
 */
public class TemperaturePresenter {

    private DataView view;

    public TemperaturePresenter(DataView view) {
        this.view = view;
    }

    /**
     * API call for getting last measured temperature from temperature sensor
     */
    public void getTempSensorData()
    {

        Call<Temperature> temperature = Utils.getApi().getLastTemperature("temperature");
        temperature.enqueue(new Callback<Temperature>()
        {
            @Override
            public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response)
            {

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

    /**
     * API call for getting average day temperatures between dates
     * @param start contains start Date
     * @param end contains end Date
     */
    public void getTempBetweenData(Date start, Date end)
    {
        //compare if dates equals
        if(start.compareTo(end) == 0)
        {
            getAverageDateTemperatures(start);
        }
        else
        {
            DatePathFormatter startFormated = new DatePathFormatter(start);
            DatePathFormatter endFormated = new DatePathFormatter(end);
            Call<List<Temperature>> temperature = Utils.getApi().getAverageTemperatureBetween("temperature",startFormated,endFormated);
            temperature.enqueue(new Callback<List<Temperature>>()
            {
                @Override
                public void onResponse(@NonNull Call<List<Temperature>> call, @NonNull Response<List<Temperature>> response)
                {

                    if (response.isSuccessful() && response.body() != null) {
                        view.setListData(response.body());

                    } else {
                        view.onErrorLoading(response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Temperature>> call, Throwable t) {
                    view.onErrorLoading(t.getLocalizedMessage());
                }

            });
        }

    }

    /**
     * Get average temperature from one day
     * Checks if dates are the same, if they are call getAverageDate, else get average days
     * @param date day from which to get average
     */
    private void getAverageDateTemperatures(Date date)
    {
        DatePathFormatter date_format = new DatePathFormatter(date);
        Call<List<Temperature>> temperature = Utils.getApi().getAverageSensorFromDayTemp(date_format);
        temperature.enqueue(new Callback<List<Temperature>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<Temperature>> call, @NonNull Response<List<Temperature>> response)
            {

                if (response.isSuccessful() && response.body() != null)
                {
                    view.setListData(response.body());

                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Temperature>> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }

        });
    }

}
