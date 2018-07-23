package com.betsy.chatsy.weatherapp.unitconversion;

import com.betsy.chatsy.weatherapp.math.ExtendedMath;


public class Conversion {

    public static double kelvinToCelsius(double kelvins) {
        double celsius = kelvins - 272.15;

        return (ExtendedMath.round(celsius, 2));
    }


}
