package com.example.android.sqliteweather.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedLocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SavedLocationKey locationName);

    @Delete
    void delete(SavedLocationKey locationName);

    @Query("SELECT * FROM locations")
    LiveData<List<SavedLocationKey>> getAllLocations();

    @Query("SELECT * FROM locations WHERE locationName = :locationQuery LIMIT 1")
    LiveData<SavedLocationKey> getLocationByName(String locationQuery);
}
