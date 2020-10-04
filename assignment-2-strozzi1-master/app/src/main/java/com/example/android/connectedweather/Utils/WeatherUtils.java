package com.example.android.connectedweather.Utils;


import com.example.android.connectedweather.data.WeatherData;
import android.net.Uri;

import com.google.gson.Gson;



import java.util.ArrayList;

public class WeatherUtils {
    private final static String WEATHER_SEARCH_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private final static String WEATHER_SEARCH_QUERY_PARAM ="q";
    private final static String WEATHER_SEARCH_CITY="Seaside";
    private final static String WEATHER_SEARCH_STATE="Oregon";
    private final static String WEATHER_SEARCH_ZIP="97138";
    private final static String WEATHER_SEARCH_ZIP_PARAM="zip";
    private final static String WEATHER_SEARCH_COUNTRY_CODE="us";

    private final static String WEATHER_SEARCH_API_KEY_PARAM="appid";
    private final static String WEATHER_SEARCH_API_KEY="57242c0186fed2f4185dfa63f1de3e90";
    private final static String WEATHER_SEARCH_UNITS_PARAM="units";
    private final static String WEATHER_SEARCH_UNITS="imperial";
    private final static String WEATHER_ICON_BASE_URL="https://openweathermap.org/img/w/";

    static class WeatherSearchResults {
        ArrayList<WeatherData> list;
    }

    public static String buildWeatherURL(String q) {
        return Uri.parse(WEATHER_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(WEATHER_SEARCH_ZIP_PARAM, WEATHER_SEARCH_ZIP)
                .appendQueryParameter(WEATHER_SEARCH_API_KEY_PARAM, WEATHER_SEARCH_API_KEY)
                .appendQueryParameter(WEATHER_SEARCH_UNITS_PARAM, WEATHER_SEARCH_UNITS)
                .build()
                .toString();
    }

    public static String buildIconURL(String suffix) {
        return WEATHER_ICON_BASE_URL + suffix + ".png";
    }

    public static ArrayList<WeatherData> parseWeatherResults(String json) {
        Gson gson = new Gson();
        WeatherSearchResults results = gson.fromJson(json, WeatherSearchResults.class);
        if (results != null && results.list != null) {
            return results.list;
        } else {
            return null;
        }
    }
}
