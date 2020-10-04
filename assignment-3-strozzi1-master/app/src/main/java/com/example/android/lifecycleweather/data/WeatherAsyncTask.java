package com.example.android.lifecycleweather.data;

import android.os.AsyncTask;

import com.example.android.lifecycleweather.utils.NetworkUtils;
import com.example.android.lifecycleweather.utils.OpenWeatherMapUtils;

import java.io.IOException;
import java.util.List;

public class WeatherAsyncTask extends AsyncTask<String, Void, String> {

    private String mUrl;
    private Callback mCallback;

    public interface Callback {
        void onSearchFinished(List<ForecastItem> searchResults);
    }

    WeatherAsyncTask(String url, Callback callback){
        mUrl = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings){
        String results = null;
        if(mUrl != null){
            try {
                results = NetworkUtils.doHTTPGet(mUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(String s) {
        List<ForecastItem> searchResults = null;
        if (s != null) {
            searchResults = OpenWeatherMapUtils.parseForecastJSON(s);
        }
        mCallback.onSearchFinished(searchResults);
    }
}
