package com.example.winecellarapp.REST;


import com.example.winecellarapp.model.Hello;
import com.example.winecellarapp.model.Temperature;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CellarAPI {


    @GET("last/{sensortype}")
    Call<Temperature> getLastTemperature(@Path("sensortype") String sensorType);

    @GET("hello")
    Call<Hello> getHello();

    @GET("average/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getAverageTemperatureBetween(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("sensordata/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getTemperatureBetween(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("/threshold/humid/{minValue}/{maxValue}")
    Call<Void> setHumidityThresholds (@Path("minValue") double minValue, @Path("maxValue") double maxValue);

    @GET("/threshold/temp/{minValue}/{maxValue}")
    Call<Void> setTemperatureThresholds (@Path("minValue") double minValue, @Path("maxValue") double maxValue);

}