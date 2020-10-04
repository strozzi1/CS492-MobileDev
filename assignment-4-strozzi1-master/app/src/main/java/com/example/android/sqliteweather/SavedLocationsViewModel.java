package com.example.android.sqliteweather;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.sqliteweather.data.SavedLocationKey;
import com.example.android.sqliteweather.data.SavedLocationRepository;

import java.util.List;

public class SavedLocationsViewModel extends AndroidViewModel {
    private SavedLocationRepository mRepository;

    public SavedLocationsViewModel(Application application){
        super(application);
        mRepository = new SavedLocationRepository(application);
    }

    public void insertSavedLocation(SavedLocationKey locationName){
        mRepository.insertSavedLocation(locationName);
    }

    public void deleteSavedLocation(SavedLocationKey locationName){
        mRepository.deleteSavedLocation(locationName);
    }

    public LiveData<List<SavedLocationKey>> getAllSavedLocations() {
        return mRepository.getAllSavedLocations();
    }

    public LiveData<SavedLocationKey> getSavedLocationByName(String locationName){
        return mRepository.getLocationByName(locationName);
    }

}
