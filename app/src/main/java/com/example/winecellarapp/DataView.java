package com.example.winecellarapp;

import java.util.List;

public interface DataView<T>{
    void setData(Object data);
    void setListData(List<T> data);
    void onErrorLoading(String message);
}
