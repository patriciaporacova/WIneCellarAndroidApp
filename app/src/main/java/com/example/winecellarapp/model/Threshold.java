package com.example.winecellarapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Threshold
 */
public class Threshold {

    @SerializedName("sensorType")
    @Expose
    private String sensorType;
    @SerializedName("minValue")
    @Expose
    private double minValue;
    @SerializedName("maxValue")
    @Expose
    private double maxValue;

    public Threshold(String sensorType, double minValue, double maxValue) {
        this.sensorType = sensorType;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

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