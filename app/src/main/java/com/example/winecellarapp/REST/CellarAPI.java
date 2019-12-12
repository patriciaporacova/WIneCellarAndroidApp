package com.example.winecellarapp.REST;


import com.example.winecellarapp.Model.Hello;
import com.example.winecellarapp.Model.Temperature;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CellarAPI {


    @GET("last/temperature")
    Call<Temperature> getLastTemperature();

    @GET("hello")
    Call<Hello> getHello();
}
