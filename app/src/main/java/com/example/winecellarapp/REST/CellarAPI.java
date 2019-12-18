package com.example.winecellarapp.REST;


import com.example.winecellarapp.model.Co2;
import com.example.winecellarapp.model.Humidity;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.model.Threshold;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CellarAPI {

    
    @GET("last/{sensortype}")
    Call<Temperature> getLastTemperature(@Path("sensortype") String sensorType);

    @GET("last/{sensortype}")
    Call<Humidity> getLastHumidity(@Path("sensortype") String sensorType);

    @GET("last/{sensortype}")
    Call<Co2> getLastAir(@Path("sensortype") String sensorType);



    @GET("average/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getAverageTemperatureBetween(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("average/{sensortype}/{startDate}/{endDate}")
    Call<List<Humidity>> getAvarageHumidityBetween(@Path("sensortype")String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("average/{sensortype}/{startDate}/{endDate}")
    Call<List<Co2>> getAverageAirBetween(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date, @Path("endDate") DatePathFormatter endDate);


    @POST("threshold/temp")
    Call<Threshold> setTempThreshold (@Body Threshold threshold);


    @GET("sensordata/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getTemperatureBetween(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("threshold/{sensortype}/{minValue}/{maxValue}")
    Call<Void> setThresholds (@Path("sensortype") String sensorType,@Path("minValue") double minValue, @Path("maxValue") double maxValue);

    @GET("getthresholds")
    Call<List<Threshold>> getThresholds();

    @GET("average/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getAverageSensorFromDates(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);
    
    @GET("minmax/{sensortype}/{startDate}/{endDate}")
    Call<List<Temperature>> getMinAndMaxFromDates(@Path("sensortype") String sensorType, @Path("startDate") DatePathFormatter date,@Path("endDate") DatePathFormatter endDate);

    @GET("avghour/{sensortype}/{date}")
    Call<List<Temperature>> getAverageSensorFromDay(@Path("sensortype") String sensorType);

    @GET("outofbounds")
    Call<List<Threshold>> getOutOfBound();
}