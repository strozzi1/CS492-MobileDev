package com.example.android.connectedweather.data;

import com.example.android.connectedweather.Utils.WeatherUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Serializable {
    public String dt_txt;           //date text
    public MainWeather main;
    public ArrayList<WeatherDetails> weather;

}
