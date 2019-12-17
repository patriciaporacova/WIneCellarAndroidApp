package com.example.winecellarapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

public class Co2 {

    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("time")
    @Expose
    private Time time;
    @SerializedName("reading")
    @Expose
    private Double reading;

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Double getReading() {
        return reading;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setReading(Double reading) {
        this.reading = reading;
    }

}
