package com.example.winecellarapp.REST;

import com.example.winecellarapp.Model.Temperature;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CellarAPI {


    @GET("temperature")
    Call<Temperature> getLastTemperature();
}
