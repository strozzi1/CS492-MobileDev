package com.example.android.lifecycleweather.data;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.lifecycleweather.utils.OpenWeatherMapUtils;

import java.util.List;

public class WeatherRepository implements WeatherAsyncTask.Callback {
    private static final String TAG = WeatherRepository.class.getSimpleName();
    private MutableLiveData<List<ForecastItem>> mWeatherResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String mCurrentQuery;
    private String mCurrentLocation;
    private String mCurrentUnits;


    public WeatherRepository(){
        mWeatherResults = new MutableLiveData<>();
        mWeatherResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);


    }

    public LiveData<Status> getLoadingStatus(){
        return mLoadingStatus;
    }

    public LiveData<List<ForecastItem>> getWeatherResults(){
        return mWeatherResults;
    }

    public void loadWeatherResults(String location, String units) {
        if(shouldExecuteSearch(location, units)){
            mCurrentLocation = location;
            mCurrentUnits = units;
            /*String url = OpenWeatherMapUtils.buildForecastURL(
                    WeatherPreferences.getDefaultForecastLocation(),
                    WeatherPreferences.getDefaultTemperatureUnits()
            );*/
            String url = OpenWeatherMapUtils.buildForecastURL(
                location,
                units
            );
            mWeatherResults.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            Log.d(TAG, "executing search withurl: " + url);
            new WeatherAsyncTask(url, this).execute();
        }
        else{
            Log.d(TAG, "using cached search results");
        }

    }

    private boolean shouldExecuteSearch(String location, String units){
        return !TextUtils.equals(location, mCurrentLocation)
                || !TextUtils.equals(units, mCurrentUnits);
    }

    @Override
    public void onSearchFinished(List<ForecastItem> results) {
        mWeatherResults.setValue(results);
        if(results != null){
            mLoadingStatus.setValue(Status.SUCCESS);
        }
        else{
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
