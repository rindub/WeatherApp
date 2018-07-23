package com.betsy.chatsy.weatherapp.model;

import java.util.Date;


public class Weather {

    private double temperature;
    private String description;
    private Date date;
    private int humidity;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }


    public String toCelsiusString() {
        return String.format("%s °C", Double.toString(this.temperature));
    }

    public String toHumidityString() {
        return String.format("Humidity: %s %%", Double.toString(this.humidity));
    }
}
