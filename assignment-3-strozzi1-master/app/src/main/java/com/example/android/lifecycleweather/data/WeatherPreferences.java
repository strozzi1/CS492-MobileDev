package com.example.android.lifecycleweather.data;

public class WeatherPreferences {
    private static final String DEFAULT_FORECAST_LOCATION = "Corvallis,OR,US";
    private static final String DEFAULT_TEMPERATURE_UNITS = "imperial";
    private static String DEFAULT_TEMPERATURE_UNITS_ABBR = "F";


    public static String getDefaultForecastLocation() {
        return DEFAULT_FORECAST_LOCATION;
    }

    public static String getDefaultTemperatureUnits() {
        return DEFAULT_TEMPERATURE_UNITS;
    }

    public static String getDefaultTemperatureUnitsAbbr() {
        return DEFAULT_TEMPERATURE_UNITS_ABBR;
    }

    public static void setTempuratureUnitsAbbr(String units){
        if(units == "imperial"){
            DEFAULT_TEMPERATURE_UNITS_ABBR="F";
        }else if(units =="metric"){
            DEFAULT_TEMPERATURE_UNITS_ABBR= "C";
        }
        else{
            DEFAULT_TEMPERATURE_UNITS_ABBR= "K";
        }
    }
}
