package com.example.winecellarapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Threshold {

    @SerializedName("date")
    @Expose
    private String sensorType;
    @SerializedName("minValue")
    @Expose
    private Double minValue;
    @SerializedName("maxValue")
    @Expose
    private Double maxValue;

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getSensorType() {
        return sensorType;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

}