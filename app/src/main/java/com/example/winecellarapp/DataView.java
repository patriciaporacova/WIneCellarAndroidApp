package com.example.winecellarapp;

import java.util.List;

public interface DataView {
    void setData(List<Model.Data> meal);
    void onErrorLoading(String message);
}
