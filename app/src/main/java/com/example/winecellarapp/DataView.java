package com.example.winecellarapp;

import com.example.winecellarapp.Model.Temperature;

import java.util.List;

public interface DataView {
    void setData(Temperature temperature);
    void onErrorLoading(String message);
}
