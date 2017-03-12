package com.example.us285899.Simpleweatherapp.utils;

/**
 * Created by US285899 on 3/9/2017.
 */

public class Constants {
    public static final String OPEN_WEATHERMAP_KEY="4933d328d31b7f96ccdc6322fe0ca17a";
    public static final String OPEN_WEATHERMAP_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=%s&lon=%s&units=metric&cnt=5&appid=" + OPEN_WEATHERMAP_KEY;
    public static final String OPEN_WEATHERICON_URL = "http://openweathermap.org/img/w/" + "%s" + ".png";

    public static final int SPLASH_DISPLAY_TIME = 2000;
    public static final String CELCIUS=" \u2103";
    public static final String PRES_UNITS="hPa";
    public static final String HUM_UNITS="%";
    public static final String SPEED_UNITS="mph";
}
