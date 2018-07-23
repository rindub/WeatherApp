package com.betsy.chatsy.weatherapp.enumaration;


public enum CallOrder {
    DESCRIPTION(1),
    HUMIDITY(0),
    DATE(2),
    TEMPERATURE(3);

    private int order;

    CallOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }
}
