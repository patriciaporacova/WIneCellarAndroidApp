package com.example.winecellarapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("sensors")
    @Expose
    private List<Data> data = null;

    public List<Data> getAllData() {
        return data;
    }

    public void setMeals(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("temperature")
        @Expose
        private String temperature;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
    }
}
