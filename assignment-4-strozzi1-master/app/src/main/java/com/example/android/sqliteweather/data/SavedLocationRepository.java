package com.example.android.sqliteweather.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedLocationRepository {
    private SavedLocationDao mDAO;

    public SavedLocationRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.savedLocationDao();
    }

    public void insertSavedLocation(SavedLocationKey locationName){
        new InsertAsyncTask(mDAO).execute(locationName);
    }

    public void deleteSavedLocation(SavedLocationKey locationName){
        new DeleteAsyncTask(mDAO).execute(locationName);
    }

    public LiveData<List<SavedLocationKey>> getAllSavedLocations(){
        return mDAO.getAllLocations();
    }

    public LiveData<SavedLocationKey> getLocationByName(String locationName){
        return mDAO.getLocationByName(locationName);
    }


    private static class InsertAsyncTask extends AsyncTask<SavedLocationKey, Void, Void> {
        private SavedLocationDao mAsyncTaskDAO;
        InsertAsyncTask(SavedLocationDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedLocationKey... savedLocations){
            mAsyncTaskDAO.insert(savedLocations[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<SavedLocationKey, Void, Void> {
        private SavedLocationDao mAsyncTaskDAO;
        DeleteAsyncTask(SavedLocationDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedLocationKey... savedLocations){
            mAsyncTaskDAO.delete(savedLocations[0]);
            return null;
        }
    }

}
