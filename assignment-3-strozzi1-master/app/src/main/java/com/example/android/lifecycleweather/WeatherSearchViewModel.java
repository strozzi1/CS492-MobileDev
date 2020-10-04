package com.example.android.lifecycleweather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.lifecycleweather.data.ForecastItem;
import com.example.android.lifecycleweather.data.Status;
import com.example.android.lifecycleweather.data.WeatherRepository;

import java.util.List;

public class WeatherSearchViewModel extends ViewModel {
    private LiveData<List<ForecastItem>> mWeatherResults;
    private WeatherRepository mRepository;
    private LiveData<Status> mLoadingStatus;

    public WeatherSearchViewModel() {
        mRepository = new WeatherRepository();
        mWeatherResults = mRepository.getWeatherResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public void loadWeatherResults(String location, String units){
        mRepository.loadWeatherResults(location, units);
    }



    public LiveData<List<ForecastItem>> getWeatherResults() {
        return mWeatherResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
