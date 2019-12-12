package com.example.winecellarapp;

import com.example.winecellarapp.Model.Hello;
import com.example.winecellarapp.Model.Temperature;

import java.util.List;

public interface DataView {
    void setData(Temperature temperature);
    void setHello(Hello hello);
    void onErrorLoading(String message);
}
