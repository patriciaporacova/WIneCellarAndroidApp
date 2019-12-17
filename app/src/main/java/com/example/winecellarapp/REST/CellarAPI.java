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

    @GET("/threshold/{sensortype}/{minValue}/{maxValue}")
    Call<Void> setHumidityThresholds (@Path("sensortype") String sensorType,@Path("minValue") double minValue, @Path("maxValue") double maxValue);

    @GET("/getThresholds")
    Call<List<Temperature>> getThresholds();

    @GET("/average/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getAverageSensorFromDates(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("/minMax/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getMinAndMaxFromDates(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("/avgHour/{sensortype}/{date}")
    Call<List<Temperature>> getAverageSensorFromDay(@Path("sensortype") String sensorType);


}