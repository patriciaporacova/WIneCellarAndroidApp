package com.example.winecellarapp.presenters;

import androidx.annotation.NonNull;

import com.example.winecellarapp.views.DataView;
import com.example.winecellarapp.REST.DatePathFormatter;
import com.example.winecellarapp.REST.Utils;
import com.example.winecellarapp.model.Co2;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Patricia Poracova
 * Class sending API calls and sending response to view trough the DataView interface
 */
public class AirPresenter
{
   private DataView view;

   public AirPresenter(DataView view)
   {this.view=view;}

    /**
     * API call for getting last measured co2 levels from temperature sensor
     */
   public void getAirSensorData()
   {
       Call<Co2> air = Utils.getApi().getLastAir("co2");
       air.enqueue(new Callback<Co2>()
                   {

                       @Override
                       public void onResponse(Call<Co2> call, Response<Co2> response) {
                           if (response.isSuccessful() && response.body() != null) {
                               view.setData(response.body());

                           } else {
                               view.onErrorLoading(response.message());
                           }
                       }

                       @Override
                       public void onFailure(Call<Co2> call, Throwable t) {
                           view.onErrorLoading(t.getLocalizedMessage());
                       }
                   }
       );
   }

    /**
     * API call for getting average day co2 levels between dates
     * @param start contains start Date
     * @param end contains end Date
     */
    public void getAirBetweenData(Date start, Date end)
    {
        DatePathFormatter startFormated = new DatePathFormatter(start);
        DatePathFormatter endFormated = new DatePathFormatter(end);
        Call<List<Co2>> air = Utils.getApi().getAverageAirBetween("co2",startFormated,endFormated);
        air.enqueue(new Callback<List<Co2>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<Co2>> call, @NonNull Response<List<Co2>> response)
            {

                if (response.isSuccessful() && response.body() != null) {
                    view.setListData(response.body());

                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Co2>> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }

        });

    }
}
