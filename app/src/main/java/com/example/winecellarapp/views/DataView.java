package com.example.winecellarapp.views;

import java.util.List;

/**
 * Interface for all presenters
 * @param <T> generic type
 */
public interface DataView<T>{
    void setData(Object data);
    void setListData(List<T> data);
    void onErrorLoading(String message);
}
