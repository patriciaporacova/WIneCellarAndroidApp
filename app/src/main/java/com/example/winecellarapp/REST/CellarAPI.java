package com.example.winecellarapp.REST;

import com.example.winecellarapp.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CellarAPI {

    //TODO: calls
    @GET("randomselection.php")
    Call<Model> getData();
}
